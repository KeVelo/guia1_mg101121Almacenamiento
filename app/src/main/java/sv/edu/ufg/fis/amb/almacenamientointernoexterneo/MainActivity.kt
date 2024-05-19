package sv.edu.ufg.fis.amb.almacenamientointernoexterneo

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.File
import java.lang.Exception
import android.Manifest
import android.app.Activity
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 101
    lateinit var boton : Button
    lateinit var texto :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        escrituraArchivosAlmacenamientoInterno(this, "archivo_almacenamiento_interno.txt", "ESTE ES UN CONTENIDO EN EL ALMACENAMIENTO INTERNO")
        escrituraArchivosAlmancenamientoExterno(this, "almacenamiento_externo.txt", "ESTE ES UN CONTENIDO EN EL ALMACENAMIENTO EXTERNO")

        boton=findViewById<Button>(R.id.btn_guardar)
        texto=findViewById<EditText>(R.id.txt_data)

        boton.setOnClickListener {
            escrituraArchivosAlmacenamientoInterno(this, "archivo_con_valor_campo_texto.txt", texto.text.toString())
        }
    }


    fun escrituraArchivosAlmacenamientoInterno(context: Context, filename: String, content: String){
        val filepath = context.filesDir.absolutePath+"/$filename"
        val file = File(filepath)
        try {
            file.writeText(content)
            Log.v("ESCRITURA EN ARCHIVO LOCAL", "RUTA: '${filepath}'" );
        }catch (e:Exception){
            e.printStackTrace();
        }

    }



    fun escrituraArchivosAlmancenamientoExterno(context: Context, filename: String, content: String){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
        val filepath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.absolutePath+"/$filename"
            val file = File(filepath)

            try {
                file.writeText(content)
                Log.v("ESCRITURA EN ALMACENAMIENTO EXTERNO", "RUTA: '${filepath}'" );
            }catch (e:Exception){
                e.printStackTrace();
            }
        }else{
            val filepath = context.getExternalFilesDir(null)!!.absolutePath+"/$filename"
            val file = File(filepath)

            if(ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                try {
                    file.writeText(content)
                    Log.v("ESCRITURA EN ALMACENAMIENTO EXTERNO", "RUTA: '${filepath}'" );
                }catch (e:Exception){
                    e.printStackTrace();
                }
            }else{
                ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
            }
        }

    }
}