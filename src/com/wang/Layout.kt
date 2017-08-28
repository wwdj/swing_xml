package com.wang

import org.jsoup.nodes.Element
import javax.swing.JPanel

/**
 * Created by MR.jian on 2017/8/26.
 */

class HLayout(frame:MyFrame,e: Element, panel: JPanel) :Layout(frame,e,panel){
    override fun onDispatch(x: Int, y: Int) {
        var widthCount = 0
        children.forEach {
            it.dispatch(x+widthCount,y,cptWidth,cptHeight)
            widthCount += it.totalWidth
        }
    }

    override fun onPrepare(width: Int, height: Int){
        var _height = 0
        var _width = 0
        children.forEach {
            it.prepare(if(width == 0) 0 else width - _width,height)
            _width += it.totalWidth
            _height = if (_height > it.totalHeight) _height else it.totalHeight
        }
        if (width == 0) cptWidth = _width
        if(height == 0) cptHeight = _height
    }
}

class VLayout(frame:MyFrame,e: Element, panel: JPanel) :Layout(frame,e,panel){

    override fun onDispatch(x: Int, y: Int) {
        var heightCount = 0
        children.forEach {
            it.dispatch(x,y+heightCount,cptWidth,cptHeight)
            heightCount += it.totalHeight
        }
    }

    override fun onPrepare(width: Int, height: Int) {
        var _height = 0
        var _width = 0
        children.forEach {
            it.prepare(width , if(height == 0) 0 else height - _height)

            _height += it.totalHeight
            _width = if (_width > it.totalWidth) _width else it.totalWidth
        }
        if (width == 0) cptWidth = _width
        if (height == 0) cptHeight = _height
    }
}
