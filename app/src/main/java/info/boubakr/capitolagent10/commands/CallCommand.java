package info.boubakr.capitolagent10.commands;

import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

import info.boubakr.capitolagent10.MainActivity;

/**
 * Created by aboubakr on 06/07/16.
 */
public  class CallCommand {

    private String contactName;
    private String phoneNumber;
    private MainActivity main;
    private boolean isContactExist;

    public CallCommand(String contactName,MainActivity main){
        this.contactName = contactName;
    }
    public void startProcess(){


    }
    private void searchContact(){
        isContactExist = false;
        ArrayList<String> contactsNames = new ArrayList<>();
        Cursor cursor = main.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                new String[] { ContactsContract.Contacts.PHOTO_ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts._ID },
                ContactsContract.Contacts.HAS_PHONE_NUMBER, null,
                ContactsContract.Contacts.DISPLAY_NAME);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            String cName = cursor.getString(1);
            if(cName.equalsIgnoreCase(contactName)){
                isContactExist = true;
              //  phoneNumber = getContactPhoneNumber(contactName);
                return;
            }
        }
    }

    /*private String getContactPhoneNumber(String contactName) {
        String phoneNumber = null;
        Cursor cursor = main.getContentResolver().
                query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{})
    }*/


}
