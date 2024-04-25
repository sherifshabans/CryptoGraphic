package com.elsharif.cryptographic

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elsharif.cryptographic.ui.theme.CryptoGraphicTheme
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    val cryptoManager=CryptoManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoGraphicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    var messageToEncrypt by remember {
                        mutableStateOf("")
                    }
                    var messageToEncrypt2 by remember {
                        mutableStateOf("")
                    }
                    var messageToDecrypt by remember {
                        mutableStateOf("")
                    }
                    var flag by remember {
                        mutableStateOf(false)
                    }

                    Column(
                        modifier= Modifier
                            .fillMaxSize()
                            .padding(32.dp)
                    ) {

                        var text ="Encrypt string"

                        var textButton ="Encrypt"


                        TextField(
                            value =  messageToEncrypt,
                            onValueChange = {
                                messageToEncrypt=it
                            },
                       //     enabled = !flag, // Make the TextField read-only
                            modifier=Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp)),
                            placeholder = { Text(text =text)}
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row {
                            Button(onClick = {
                                val bytes =messageToEncrypt.encodeToByteArray()
                                val file=File(filesDir,"secret.text")
                                if(!file.exists()){
                                    file.createNewFile()
                                }
                                val fos=FileOutputStream(file)

                               // if(!flag) {
                                    flag = true

                                    messageToDecrypt = cryptoManager.encrypt(
                                        bytes = bytes,
                                        outputStream = fos
                                    ).decodeToString()
                                    messageToEncrypt = ""


                              //  }
                               /* else {
                                    val file = File(filesDir,"secret.text")
                                    messageToEncrypt=cryptoManager.decrypt(
                                        inputStream = FileInputStream(file)
                                    ).decodeToString()

                                     flag=false
                               messageToEncrypt = messageToDecrypt

                                }*/
                            })
                            {

                                Text(text = textButton)
                            }
                       //     Spacer(modifier = Modifier.width(16.dp))





                        }


                        Text(
                            text = messageToDecrypt,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .padding(16.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer)

                        )

                        if(flag){
                            Button(onClick = {
                                val file = File(filesDir,"secret.text")
                                messageToEncrypt=cryptoManager.decrypt(
                                    inputStream = FileInputStream(file)
                                ).decodeToString()

//                                messageToEncrypt = messageToDecrypt


                            }) {
                                Text(text = "Decrypt")

                            }


                        }


                    }

                }
            }
        }
    }
}
