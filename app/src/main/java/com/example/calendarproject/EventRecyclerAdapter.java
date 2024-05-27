package com.example.calendarproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import lombok.NonNull;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder>{
    Context context;
    ArrayList<Events> arrayList;
    DBOpenHelper dbOpenHelper;
    public EventRecyclerAdapter(Context context, ArrayList<Events> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @androidx.annotation.NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_rowlayout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull MyViewHolder holder, int position) {
        Events events = arrayList.get(position);
        holder.Event.setText(events.getEVENT());
        holder.DateTxt.setText(events.getDATE());
        holder.Time.setText(events.getTIME());
        holder.delete.setOnClickListener(v -> {
            deleteCalendarEvent(events.getEVENT(),events.getDATE(),events.getTIME());
            arrayList.remove(position);
            notifyDataSetChanged();
        });
        if(isAlarmed(events.getDATE(), events.getEVENT(), events.getTIME())){
            holder.setAlarm.setImageResource(R.drawable.ic_action_alarm_on);
            notifyDataSetChanged();
        }else{
            holder.setAlarm.setImageResource(R.drawable.ic_action_alarm_off);
            notifyDataSetChanged();
        }
        Calendar dateCalendar=Calendar.getInstance();
        dateCalendar.setTime(ConvertStringToDate(events.getDATE()));
        int alarmYear=dateCalendar.get(Calendar.YEAR);
        int alarmMonth=dateCalendar.get(Calendar.MONTH);
        int alarmDay=dateCalendar.get(Calendar.DAY_OF_MONTH);
        Calendar timeCalendar=Calendar.getInstance();
        timeCalendar.setTime(ConvertStringToTime(events.getTIME()));
        int alarmHour=timeCalendar.get(Calendar.HOUR_OF_DAY);
        int alarmMinute=timeCalendar.get(Calendar.MINUTE);


        holder.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAlarmed(events.getDATE(), events.getEVENT(), events.getTIME())){
                    holder.setAlarm.setImageResource(R.drawable.ic_action_alarm_off);
                    cancelAlarm(getRequestCode(events.getDATE(), events.getEVENT(), events.getTIME()));
                    updateEvent(events.getDATE(), events.getEVENT(), events.getTIME(), "off");
                    notifyDataSetChanged();
                }
                else{
                    holder.setAlarm.setImageResource(R.drawable.ic_action_alarm_on);
                    Calendar alarmCalendar=Calendar.getInstance();
                    alarmCalendar.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
                    setAlarm(alarmCalendar,events.getEVENT(),events.getTIME(),
                            getRequestCode(events.getDATE(),events.getEVENT(),events.getTIME()));
                    updateEvent(events.getDATE(), events.getEVENT(), events.getTIME(), "on");
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView DateTxt, Event,Time;
        Button delete;
        ImageButton setAlarm;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            DateTxt = itemView.findViewById(R.id.eventdatee);
            Event = itemView.findViewById(R.id.eventnamee);
            Time = itemView.findViewById(R.id.eventtimee);
            delete = itemView.findViewById(R.id.delete);
            setAlarm = itemView.findViewById(R.id.alarmmeBtn);
        }
    }

    public Date ConvertStringToDate (String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(eventDate);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public Date ConvertStringToTime (String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(eventDate);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    private void deleteCalendarEvent(String event,String date,String time){
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.deleteEvent(event,date,time,database);
        dbOpenHelper.close();
    }

    private boolean isAlarmed(String date, String event, String time){
        boolean alarmed=false;
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadIDEvents(date,event,time,database);
        while (cursor.moveToNext()){
            String notify = cursor.getString(cursor.getColumnIndexOrThrow (DBStructure.Notify));
            if(notify.equals("on")){
                alarmed=true;
            }else{
                alarmed=false;
            }
        }
        cursor.close();
        dbOpenHelper.close();
        return alarmed;
    }

    private void setAlarm(Calendar calendar, String event, String time, int requestCode){
        Intent intent=new Intent(context.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("event", event);
        intent.putExtra("time", time);
        intent.putExtra("id", requestCode);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager= (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm(int requestCode){
        Intent intent=new Intent(context.getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager= (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private int getRequestCode(String date, String event, String time){
        int code=0;
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadIDEvents(date, event, time,database);
        while (cursor.moveToNext()){
            code = cursor.getInt(cursor.getColumnIndexOrThrow (DBStructure.ID));
        }
        cursor.close();
        dbOpenHelper.close();

        return code;
    }

    private void updateEvent(String date, String event, String time, String notify){
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.updateEvent(date,event,time,notify,database);
        dbOpenHelper.close();
    }
}
