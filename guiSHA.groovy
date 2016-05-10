import javax.swing.*
import java.awt.*
import java.awt.event.*
import java.security.*

import java.security.MessageDigest

def myWindow = new window()


public class window extends JFrame implements ActionListener{

    def print = new JButton("Print")
    def pwField = new JPasswordField()
    def pwLabel = new JLabel("Password")

    window(def w = 500, def h = 500) {
        this.setVisible(true)
        this.setSize(w,h)
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
        this.setLayout(null)

        print.setSize(100,35)
        print.setLocation(250,25)
        print.setVisible(true)
        print.addActionListener(this)
        this.add(print)

        pwField.setSize(100,35)
        pwField.setLocation(100,25)
        pwField.setVisible(true)
        this.add(pwField)

        pwLabel.setSize(100,35)
        pwLabel.setLocation(25,25)
        pwLabel.setVisible(true)
        this.add(pwLabel)

        updateObject(this)
    }

    def updateObject(def objRef) {
        objRef.validate()
        objRef.repaint()
    }

    def passSHA(String pw) {
        def shaInstance = MessageDigest.getInstance("SHA-256")
        def shaArray = []
        shaInstance.digest(pw.getBytes()).each { entry ->
            shaArray.push(entry)
        }
        return shaArray.join(':')
    }

    public void actionPerformed(ActionEvent evt) {
        //println evt.getActionCommand()
        println passSHA(pwField.getPassword().toString())
    }

}
