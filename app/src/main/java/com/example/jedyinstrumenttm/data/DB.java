package com.example.jedyinstrumenttm.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jedyinstrumenttm.data.model.ActiveOrder;
import com.example.jedyinstrumenttm.data.model.Instrument;
import com.example.jedyinstrumenttm.data.model.User;

import java.util.Vector;

public class DB {
    public static Vector<User> listUsers = new Vector<>();
    public static Vector<Instrument> listInstruments = new Vector<>();
    public static Vector<ActiveOrder> listActiveOrders = new Vector<>();

    static boolean HAS_SYNCED = false;
    static boolean HAS_INSERTED = false;

    public static void syncData(Context ctx){
        if (HAS_SYNCED) return;

        DBHelper helper = new DBHelper(ctx);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor_user = db.rawQuery("SELECT * FROM User", null);
        while (cursor_user.moveToNext()){
            int userID = cursor_user.getInt(0);
            String username = cursor_user.getString(1);
            String email = cursor_user.getString(2);
            String password = cursor_user.getString(3);
            String phoneNumber = cursor_user.getString(4);

            listUsers.add(new User(userID, username, email, password, phoneNumber));
        }
        cursor_user.close();

        Cursor cursor_instrument = db.rawQuery("SELECT * FROM Instrument", null);
        while (cursor_instrument.moveToNext()){
            int instrumentID = cursor_instrument.getInt(0);
            String instrumentName = cursor_instrument.getString(1);
            double instrumentRating = cursor_instrument.getDouble(2);
            double instrumentPrice = cursor_instrument.getDouble(3);
            String instrumentImagePath = cursor_instrument.getString(4);
            String instrumentDescription = cursor_instrument.getString(5);

            listInstruments.add(new Instrument(instrumentID, instrumentName, instrumentRating, instrumentPrice, instrumentImagePath, instrumentDescription));
        }
        cursor_instrument.close();

        Cursor cursor_active_order = db.rawQuery("SELECT * FROM ActiveOrder", null);
        while (cursor_active_order.moveToNext()){
            int activeOrderID = cursor_active_order.getInt(0);
            int userID = cursor_active_order.getInt(1);
            int instrumentID = cursor_active_order.getInt(2);
            int instrumentQuantity = cursor_active_order.getInt(3);

            listActiveOrders.add(new ActiveOrder(activeOrderID, userID, instrumentID, instrumentQuantity));
        }
        cursor_active_order.close();

        HAS_SYNCED = true;
    }

    public static void insertDataUser (String username, String email, String password, String phoneNumber, Context ctx){
        int id = 0;
        if(listUsers.size() == 0){
            id = 1;
        }
        else{
            id = listUsers.get(listUsers.size()-1).userID + 1;
        }

        //add ke vector
        listUsers.add(new User(id, username, email, password, phoneNumber));

        //add ke database
        String SQL_INSERT_DATA_USER = "INSERT INTO User VALUES (null, '"+ username +"', '"+ email +"', '"+ password +"', '"+ phoneNumber +"')";
        DBHelper helper = new DBHelper(ctx);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL(SQL_INSERT_DATA_USER);

        //for checking
        Log.d("INSERT USER", "User inserted successfully!");
    }

    public static void insertDataInstrument (String instrumentName, double instrumentRating, double instrumentPrice, String instrumentImagePath, String instrumentDescription, Context ctx){
        int id = 0;
        if(listInstruments.size() == 0){
            id = 1;
        }
        else{
            id = listInstruments.get(listInstruments.size()-1).instrumentID + 1;
        }

        //add ke vector
        listInstruments.add(new Instrument(id, instrumentName, instrumentRating, instrumentPrice, instrumentImagePath, instrumentDescription));

        //add ke database
        String SQL_INSERT_DATA_INSTRUMENT = "INSERT INTO Instrument VALUES (null, '"+ instrumentName +"', '"+ instrumentRating +"', '"+ instrumentPrice +"', '"+ instrumentImagePath +"', '"+ instrumentDescription +"')";
        DBHelper helper = new DBHelper(ctx);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL(SQL_INSERT_DATA_INSTRUMENT);

        //for checking
        Log.d("INSERT INSTRUMENT", "Instrument inserted successfully!");
    }

    public static void insertDataActiveOrder (int userID, int instrumentID, int instrumentQuantity, Context ctx){
        int id = 0;
        if(listActiveOrders.size() == 0){
            id = 1;
        }
        else{
            id = listActiveOrders.get(listActiveOrders.size()-1).activeOrderID + 1;

        }

        //add ke vector
        listActiveOrders.add(new ActiveOrder(id, userID, instrumentID, instrumentQuantity));

        //add ke database
        String SQL_INSERT_DATA_ACTIVE_ORDER = "INSERT INTO ActiveOrder VALUES (null, '"+ userID +"', '"+ instrumentID +"', '"+ instrumentQuantity +"')";
        DBHelper helper = new DBHelper(ctx);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL(SQL_INSERT_DATA_ACTIVE_ORDER);

        //for checking
        Log.d("INSERT ACTIVE ORDER", "Active Order inserted successfully!");
    }
    public static void updateQuantityCart (int userID, int instrumentID, int instrumentQuantity, Context ctx){
        ActiveOrder updatedCart = null;
        for (ActiveOrder activeOrder:listActiveOrders){
            if (activeOrder.userID == userID && activeOrder.instrumentID == instrumentID){
                updatedCart = activeOrder;
            }
        }

        if (updatedCart == null) return;

        updatedCart.instrumentQuantity = instrumentQuantity;

        String SQL_UPDATE_CART_QUANTITY = "UPDATE ActiveOrder SET instrumentQuantity = "+instrumentQuantity+" WHERE activeOrderID = "+ updatedCart.activeOrderID +"";

        DBHelper helper = new DBHelper(ctx);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(SQL_UPDATE_CART_QUANTITY);

        //for checking
        Log.d("UPDATE CART QUANTITY", "Cart Quantity Updated!");
    }

    public static void deleteCart(int activeOrderID, Context ctx){
        ActiveOrder deletedCart = null;
        for (ActiveOrder activeOrder:listActiveOrders){
            if (activeOrder.activeOrderID == activeOrderID){
                deletedCart = activeOrder;
            }
        }

        if (deletedCart == null) return;

        listActiveOrders.remove(activeOrderID-1);

        String whereClause = "activeOrderID = ?";
        String[] whereArgs = {String.valueOf(activeOrderID)};

        DBHelper helper = new DBHelper(ctx);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete("ActiveOrder", whereClause, whereArgs);

        //for checking
        Log.d("DELETE CART", "Cart Deleted!");
    }

    public static void generateDummyData(Context ctx){
        if (HAS_INSERTED) return;

        String username_1 = "Sheila Gracia";
        String email_1 = "sheila.angelina@binus.com";
        String password_1 = "12345678";
        String phone_number_1 = "08567017461";

        insertDataUser(username_1, email_1, password_1, phone_number_1, ctx);

        String instrument_name_1 = "Dave Smith Sequential Take 5 – Synthesizer";
        double instrument_rating_1 = 4.6;
        double instrument_price_1 = 32312000;
        String instrument_image_path_1 = "dave_smith_sequential_take_5_synthesizer";
        String instrument_desc_1 = "Sequential has just introduced the Take 5, an all-new, compact and portable five-voice VCO/VCF-based poly synth designed with both synth newcomers and space-conscious pros in mind.\n" +
                "\n" +
                "A full-featured subtractive synthesizer, the Take 5 sports two VCOs and a sub oscillator per voice, a classic 4-pole, resonant analog filter from the Prophet-5 Rev 4 design, 44 full-size keys, and a premium Fatar keybed. Its clever key-split feature gives players access to a wider playable range than many other compact synths by enabling players to divide the 3.5 octave keyboard into two separate performance zones. Dual Digital effects and a dedicated overdrive, continuously variable wave shaping, and front-panel access to analog FM ensure the Take 5 can cover a wide range of sonic territory, from vintage to modern.\n" +
                "\n" +
                "“The Take 5 puts the classic Sequential sound and legacy into the hands of people who may not have been able to experience it before,” said Dave Smith, Sequential founder and lead product designer. “Packing this much performance power into such a small footprint was the kind of challenge we love. Throughout development we were amazed by how huge this synth sounds and how crazy versatile it is. It was a joy to work on and I’m looking forward to hearing what our customers create with it.”\n" +
                "\n" +
                "This versatility comes from the Take 5’s sound engine. Two voltage-controlled oscillators provide traditional sine, saw, and pulse waves with waveshaping, while simple analog FM adds harmonically complex, bell-like FM timbres at need. The Prophet-5-lineage 4-pole analog filter shapes the sound of the oscillators and sub oscillator in classic fashion while a Drive control provides additional punch. For still more warmth, a Vintage knob adds micro variations to oscillator, filter, and envelope behavior from voice to voice.\n" +
                "\n" +
                "The Take 5 feature set is rounded out with two LFOs (1 global, and 1 per-voice LFO), ADSR+delay envelopes, dual digital effects (a dedicated reverb and a suite of time-delay and other effects) a separate additional overdrive effect, and extensive modulation possibilities. A 64-step polyphonic sequencer and multimode arpeggiator complete the picture.\n" +
                "\n" +
                "Sequential CEO David Gibbons summed up the Take 5 this way: “For players that have wanted to get into analog poly synths but have been put off by price or complexity, the Take 5 is the perfect gateway to subtractive synthesis and the creative power of Sequential’s best synths. It is pure Sequential sound and quality at a price that’s within reach of almost anyone. Portability is also a huge plus — you can transport it from studio to stage without breaking a sweat or leaving any features or playability behind.”";

        insertDataInstrument(instrument_name_1, instrument_rating_1, instrument_price_1, instrument_image_path_1, instrument_desc_1, ctx);

        String instrument_name_2 = "Yamaha Guitar Acoustic CPX600 Black";
        double instrument_rating_2 = 5.00;
        double instrument_price_2 = 3625225;
        String instrument_image_path_2 = "yamaha_guitar_acoustic_cpx600_black";
        String instrument_desc_2 = "CPX guitars combine the on-stage prowess of the legendary APX series with a bigger-bodied design for a louder, fuller acoustic sound and a more traditional look.\n" +
                "\n" +
                "\n" +
                "Combining the playability and clear, dynamic plugged in tone of Yamaha’s APX electro-acoustic guitars with a bigger body for traditional looks and a more powerful, louder acoustic sound, the CPX600 offers the ideal balance between plugged in performance and acoustic style.\n" +
                "\n" +
                "Medium Jumbo Body For Full-bodied Acoustic Tone\n" +
                "634mm Scale Length for Enhanced Comfort\n" +
                "Narrower String Spacing Makes Stretching for Chords Easier\n" +
                "New Bracing Pattern For Tighter Tone\n" +
                "Stage-focused Pickup Sound to Cut Through a Mix";

        insertDataInstrument(instrument_name_2, instrument_rating_2, instrument_price_2, instrument_image_path_2, instrument_desc_2, ctx);

        HAS_INSERTED = true;
    }
}
