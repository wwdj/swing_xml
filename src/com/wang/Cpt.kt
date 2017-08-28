package com.wang

import org.jsoup.nodes.Element
import javax.swing.*

/**
 * Created by MR.jian on 2017/8/24.
 */
class Button(frame:MyFrame,e:Element,panel: JPanel)
    :ComponentView<JButton>(frame,e,panel){
    override var wrapWidth: Int = 60
    override var wrapHeight: Int = 25
    override fun create(width: Int, height: Int): JButton {
        return JButton().apply {
            setSize(width,height)
        }
    }

    override fun onOtherAttr(key: String, value: String) {
        when(key){
            "text"->component.text = value
        }
    }
}

class Label(frame:MyFrame,e:Element,panel: JPanel)
    :ComponentView<JLabel>(frame,e,panel),TextView{
    override var text: String
        get() = component.text
        set(value) {component.text = value}
    override var wrapWidth: Int = 60
    override var wrapHeight: Int = 30
    override fun create(width: Int, height: Int): JLabel {
        return JLabel().apply {
            setSize(width, height)
        }
    }

    override fun onOtherAttr(key: String, value: String) {
        when(key){
            "text" -> {
                text = value
            }
        }
    }
}

class Text(frame:MyFrame,e:Element,panel: JPanel)
    :ComponentView<JTextField>(frame,e,panel),TextView{
    override var text: String
        get() = component.text
        set(value) {component.text = value}
    override var wrapWidth: Int = 60
    override var wrapHeight: Int = 30
    override fun create(width: Int, height: Int): JTextField {
        return JTextField().apply {
            setSize(width, height)
        }
    }

    override fun onOtherAttr(key: String, value: String) {
        when(key) {
            "text" -> {
                text = value
            }
        }
    }
}