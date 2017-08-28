package com.wang

import org.jsoup.nodes.Element
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * Created by MR.jian on 2017/8/24.
 */

abstract class Layout(frame:MyFrame,e: Element,panel: JPanel) :View(frame,e,panel){
    override var wrapWidth: Int = 0
    override var wrapHeight: Int = 0
    protected val children:Array<View> = Array(e.children().size, {
        frame.viewFactory(e.child(it),panel)
    })


    override fun onOtherAttr(key: String, value: String) {
        when(key){
            "gravity"->{
                when(value){
                    "vertical"->{
                        children.forEach { it.v_center = true }
                    }
                    "horizontal"->{
                        children.forEach { it.h_center = true }
                    }
                }
            }
        }
    }
}

abstract class View(frame:MyFrame,val e: Element,val panel: JPanel){
    private val margin = arrayOf(0,0,0,0)
    abstract val wrapWidth:Int
    abstract val wrapHeight:Int
    protected var cptWidth = 0
    protected var cptHeight = 0
    var totalWidth = 0
    var totalHeight = 0
    var h_center = false
    var v_center = false

    init{
        val attrs = e.attributes()
        attrs.forEach {
            when(it.key){
                "margin_top" -> margin[0] = it.value.toInt()
                "margin_bottom" -> margin[1] = it.value.toInt()
                "margin_left" -> margin[2] = it.value.toInt()
                "margin_right" -> margin[3] = it.value.toInt()
            }
        }
    }

    //pWidth,pHeight是父布局的长宽（父布局调用的）
    open fun prepare(pWidth:Int,pHeight:Int){
        cptWidth = when(e.attr("width")){
            "match_parent"->if(pWidth == 0) wrapWidth else pWidth - margin[2] - margin[3]
            "wrap_content"->wrapWidth
            ""->wrapWidth
            else->e.attr("width").toInt()
        }
        cptHeight = when(e.attr("height")){
            "match_parent"->if(pHeight == 0) wrapHeight else pHeight- margin[0] - margin[1]
            "wrap_content"->wrapHeight
            ""->wrapHeight
            else->e.attr("height").toInt()
        }
        //设置长宽
        onPrepare(cptWidth,cptHeight)
        totalWidth = cptWidth+margin[2]+margin[3]
        totalHeight = cptHeight+margin[0]+margin[1]
    }

    //根据方法中的参数，设置控件大小
    abstract fun onPrepare(width:Int,height:Int)

    //对其他的属性的处理(不要出现会调整长宽的属性)
    open fun onOtherAttr(key:String,value:String){}

    //添加时，处理居中属性，和其他自定义属性
    fun dispatch(x:Int,y:Int,pWidth:Int,pHeight:Int){
        if (h_center){
            margin[2] = (pWidth - cptWidth) / 2
            margin[3] = margin[2]
            totalWidth = pWidth
        }
        if(v_center){
            margin[0] = (pHeight - cptHeight) / 2
            margin[1] = margin[0]
            totalHeight = pHeight
        }

        e.attributes().forEach {
            onOtherAttr(it.key,it.value)
        }
        onDispatch(x+margin[2],y+margin[0])
    }

    abstract fun onDispatch(x:Int,y:Int)
}

abstract class ComponentView <T :JComponent>(frame:MyFrame,e: Element,panel: JPanel) :View(frame,e,panel){
    lateinit var component: T

    override fun onPrepare(width: Int, height: Int){
        component = create(width,height)
    }
    abstract fun create(width:Int,height: Int): T
    override fun onDispatch(x: Int, y: Int) {
        component.setLocation(x,y)
        panel.add(component)
    }
}

interface TextView{
    var text:String
}
