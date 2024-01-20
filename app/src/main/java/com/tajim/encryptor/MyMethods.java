package com.tajim.encryptor;


import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MyMethods {
    public static String encryptData(String text, String pass) throws Exception{
        byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
        byte[] passByte = pass.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(passByte, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] securedByte = cipher.doFinal(textByte);


        return Base64.encodeToString(securedByte, Base64.DEFAULT);


    }

    public static String decryptData(String text, String pass)throws Exception{

        byte[] decodedBytes = Base64.decode(text, Base64.DEFAULT);
        byte[] passByte = pass.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(passByte, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedbyte = cipher.doFinal(decodedBytes);

        return new String(decryptedbyte, StandardCharsets.UTF_8);



    }

    public static String encryptImg(ImageView imageView)throws Exception{


        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

        byte[] imageByte = byteArrayOutputStream.toByteArray();





        return  Base64.encodeToString(imageByte, Base64.DEFAULT);

    }

    public static Bitmap decryptImage(String base64encoded)throws Exception{

        byte[] decodeBytes = Base64.decode(base64encoded, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodeBytes, 0, decodeBytes.length);

        return decodedBitmap;


    }

    public static void CopyClip (Context context, String text){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clip = ClipData.newPlainText("Copied Text", text);

        clipboard.setPrimaryClip(clip);
    }

    public static String getClipboardText(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        // Check if there is any clipboard content and if it is text
        if (clipboard.hasPrimaryClip()) {
            ClipData clipData = clipboard.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                CharSequence clipboardText = clipData.getItemAt(0).getText();  // Get the text from the clipboard
                if (clipboardText != null) {
                    return clipboardText.toString();  // Convert to String
                }
            }
        }
        return null;  // Return null if no text is available
    }



}
