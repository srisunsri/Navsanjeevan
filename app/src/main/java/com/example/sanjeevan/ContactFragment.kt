package com.example.sanjeevan

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class ContactFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var messageEditText: EditText
    private lateinit var sendMessageButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        nameEditText = view.findViewById(R.id.nameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        messageEditText = view.findViewById(R.id.messageEditText)
        sendMessageButton = view.findViewById(R.id.sendMessageButton)
//
//        sendMessageButton.setOnClickListener {
//            val name = nameEditText.text.toString()
//            val email = emailEditText.text.toString()
//            val message = messageEditText.text.toString()
//
//            if (name.isNotEmpty() && email.isNotEmpty() && message.isNotEmpty()) {
//                SendEmailTask(name, email, message).execute()
//            } else {
//                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            }
//        }

        return view
    }

    private inner class SendEmailTask(
        private val name: String,
        private val email: String,
        private val message: String
    ) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void?): Boolean {
            return try {
                sendEmail(name, email, message)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        override fun onPostExecute(success: Boolean) {
            if (success) {
                Toast.makeText(context, "Message sent successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to send message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmail(name: String, email: String, message: String) {
        val recipient = "nst@navsanjeevansocialtrust.org"
        val subject = "Contact Us Message from $name"
        val content = "Name: $name\nEmail: $email\nMessage: $message"

        // Assuming you have a valid SMTP server
        val props = System.getProperties()
        props["mail.smtp.host"] = "your.smtp.server"
        props["mail.smtp.port"] = "25"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"

        val session = Session.getInstance(props, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("your_email", "your_password")
            }
        })

        val mimeMessage = MimeMessage(session)
        mimeMessage.setFrom(InternetAddress("your_email"))
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
        mimeMessage.subject = subject
        mimeMessage.setText(content)

        Transport.send(mimeMessage)
    }
}
