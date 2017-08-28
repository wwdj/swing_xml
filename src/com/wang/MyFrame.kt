package com.wang

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.awt.Toolkit
import java.awt.event.ActionListener
import java.io.File
import java.util.*
import javax.swing.AbstractButton
import javax.swing.JFrame
import javax.swing.JPanel

class MyFrame(val frameName:String){
    companion object{
        val viewClass = HashMap<String,Class<out View>>()
        init {
            viewClass.put("h_layout",HLayout::class.java)
            viewClass.put("v_layout",VLayout::class.java)
            viewClass.put("button",Button::class.java)
            viewClass.put("label",Label::class.java)
            viewClass.put("text",Text::class.java)
        }

        fun registerCpt(tag:String,cpt:Class<out View>){
            viewClass.put(tag,cpt)
        }
    }


    //id->view
    private val id_viewMap = HashMap<String,View>()
    fun findCptById(id:String) = (id_viewMap[id] as ComponentView<*>).component

    fun getText(id:String):String{
        return (id_viewMap[id] as TextView).text
    }

    fun addListener(id:String,l :ActionListener){
        (findCptById(id) as AbstractButton).addActionListener(l)
    }

    fun viewFactory(e:Element,panel:JPanel):View
            = viewClass[e.tagName()]!!
            .getConstructor(MyFrame::class.java,Element::class.java,JPanel::class.java)
            .newInstance(this,e,panel).apply {
        if (e.attr("id") != "") id_viewMap.put(e.attr("id"),this)
    }

    fun inflate(docFile: File, width:Int, height:Int){
        val doc = Jsoup.parse(docFile,"UTF-8")
        val f = JFrame(frameName)
        f.setSize(width+16,height+39)
        f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val p = JPanel(null)

        val root = viewFactory(doc.body().child(0),p)
        root.prepare(width,height)
        root.dispatch(0,0,width,height)

        f.add(p)
        val screenSize = Toolkit.getDefaultToolkit().getScreenSize()
        f.setLocation((screenSize.width - width)/2,(screenSize.height - height)/2)
        f.isVisible = true
    }
}


