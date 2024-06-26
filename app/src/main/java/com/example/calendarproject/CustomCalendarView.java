package com.example.calendarproject;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CustomCalendarView extends LinearLayout {
    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    DBOpenHelper dbOpenHelper;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFomarte = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    SimpleDateFormat eventDateFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List<Events> evensList = new ArrayList<>();
    int alarmYear,alarmMonth,alarmDay,alarmHour,alarmMinute;

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        InitializeLayout();
        SetUpCalendar();

// set onclick cho nút trước
        PreviousButton.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            SetUpCalendar();
        });
        NextButton.setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, +1);
            SetUpCalendar();
        });
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            final View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_layout, null);
            final EditText EventName = addView.findViewById(R.id.events_name);
            final TextView EventTime = addView.findViewById(R.id.eventtime);
            ImageButton SetTime = addView.findViewById(R.id.seteeventtime);
            CheckBox alarmMe=addView.findViewById(R.id.alarmme); //checkbox thông báo
            Calendar dateCalendar = Calendar.getInstance();
            dateCalendar.setTime(dates.get(position));
            alarmYear=dateCalendar.get(Calendar.YEAR);
            alarmMonth=dateCalendar.get(Calendar.MONTH);
            alarmDay=dateCalendar.get(Calendar.DAY_OF_MONTH);

            Button AddEvent = addView.findViewById(R.id.addevent);
            // bấm vào đồng hồ thì nó hiển thị thời gian để chọn
            SetTime.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
//                TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), new TimePickerDialog.OnTimeSetListener()
//                        , (view1, hourOfDay, minute) -> {
//                            Calendar c = Calendar.getInstance();
//                            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                            c.set(Calendar.MINUTE, minutes);
//                            c.setTimeZone(TimeZone.getDefault());
//                            SimpleDateFormat hformate = new SimpleDateFormat("K:mm a", Locale.ENGLISH);
//                            String event_Time = hformate.format(c.getTime());
//                            EventTime.setText(event_Time);
//                            alarmHour=c.get(Calendar.HOUR_OF_DAY);
//                            alarmMinute=c.get(Calendar.MINUTE);
//
//                        }, hours, minutes, false);
//                timePickerDialog.show();
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minutes);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat hformate = new SimpleDateFormat("K:mm a", Locale.ENGLISH);
                        String event_Time = hformate.format(c.getTime());
                        EventTime.setText(event_Time);
                        alarmHour=c.get(Calendar.HOUR_OF_DAY);
                        alarmMinute=c.get(Calendar.MINUTE);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            });
            final String date = eventDateFormate.format(dates.get(position));
            final String month = monthFormat.format(dates.get(position));
            final String year = yearFomarte.format(dates.get(position));
            AddEvent.setOnClickListener(v -> {

                if(alarmMe.isChecked()){
                    SaveEVent(EventName.getText().toString(), EventTime.getText().toString(), date, month, year, "on");
                    SetUpCalendar();
                    Calendar calendar=Calendar.getInstance();
                    calendar.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
                    setAlarm(calendar, EventName.getText().toString(), EventTime.getText().toString(),
                            getRequestCode(date,EventName.getText().toString(), EventTime.getText().toString()));
                    alertDialog.dismiss();
                }else{
                    SaveEVent(EventName.getText().toString(), EventTime.getText().toString(), date, month, year, "off");
                    SetUpCalendar();
                    alertDialog.dismiss();
                }
            });

            builder.setView(addView);
            alertDialog = builder.create();
            alertDialog.show();
        });
       gridView.setOnItemLongClickListener((parent, view, position, id) -> {
           String date = eventDateFormate.format(dates.get(position));
           AlertDialog.Builder builder = new AlertDialog.Builder(context);
           builder.setCancelable(true);
           View showView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_layout,null);
           RecyclerView recyclerView = showView.findViewById(R.id.EventsRV);
           RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
           recyclerView.setLayoutManager(layoutManager);
           recyclerView.setHasFixedSize(true);
           EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext(),
                   CollectEventByDate(date));
           recyclerView.setAdapter(eventRecyclerAdapter);
           eventRecyclerAdapter.notifyDataSetChanged();

           builder.setView(showView);
           alertDialog = builder.create();
           alertDialog.show();
           alertDialog.setOnCancelListener(dialog -> {
               SetUpCalendar();
           });
           return true;
       });
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

    private void setAlarm(Calendar calendar, String event, String time, int requestCode){
        Intent intent=new Intent(context.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("event", event);
        intent.putExtra("time", time);
        intent.putExtra("id", requestCode);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager= (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private ArrayList<Events> CollectEventByDate (String date){
        ArrayList<Events> arrayList = new ArrayList<>();
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.Readvents(date,database);
        while (cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndexOrThrow (DBStructure.EVENT));
            String time = cursor.getString (cursor.getColumnIndexOrThrow (DBStructure.TIME));
            String datee = cursor.getString (cursor.getColumnIndexOrThrow (DBStructure.DATE));
            String Month = cursor.getString (cursor.getColumnIndexOrThrow (DBStructure.MONTH));
            String Year = cursor.getString (cursor.getColumnIndexOrThrow (DBStructure.YEAR));
            Events events = new Events(event,time,datee,Month,Year);
            arrayList.add(events);
        }
        cursor.close();
        dbOpenHelper.close();
        return arrayList;
    }
    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void SaveEVent(String event, String time, String date, String month, String year, String notify) {
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveEvent(event, time, date, month, year, notify, database);
        dbOpenHelper.close();
        //khong hieu
        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();
    }

    //gắn layout với các dữ liệu trong lớp java này
    private void InitializeLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        NextButton = view.findViewById(R.id.nextBtn);
        PreviousButton = view.findViewById(R.id.previousBtn);
        CurrentDate = view.findViewById(R.id.current_Date);
        gridView = view.findViewById(R.id.gridView);
    }

    //set thời gian hiện tại cho ô text
    public void SetUpCalendar() {
        String currentDate = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDate);
        //clear các ngày để chuẩn bị cho việc thiết lập lịch mới
        dates.clear();
        //tạo bản sao để không thay đổi đối tượng gốc
        Calendar monthCalendar = (Calendar) calendar.clone();
        //Thiết lập monthCalendar là ngày đầu tiên của tháng hiện tại
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        //Lấy ngày trong tuần của ngày đầu tiên của tháng
        int FirstDayMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        //lùi lại 'FirstDayMonth' ngày để tìm ngày bắt đầu hiển thị trên lịch. Đảm bảo rằng lịch bắt đầu từ ngày đầu tuần
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayMonth);
        CollectEventsPerMonth(monthFormat.format(calendar.getTime()),yearFomarte.format(calendar.getTime()));
        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            //tăng ngày của monthcalendar lên một ngày
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        myGridAdapter = new MyGridAdapter(context, dates, calendar, evensList);
        gridView.setAdapter(myGridAdapter);
    }
    private void CollectEventsPerMonth (String month, String year){
        evensList.clear();
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadventsperMonth(month,year,database);
        while (cursor.moveToNext()) {
            String event = cursor.getString(cursor.getColumnIndexOrThrow (DBStructure.EVENT));
            String time = cursor.getString (cursor.getColumnIndexOrThrow (DBStructure.TIME));
            String date = cursor.getString (cursor.getColumnIndexOrThrow (DBStructure.DATE));
            String Month = cursor.getString (cursor.getColumnIndexOrThrow (DBStructure.MONTH));
            String Year = cursor.getString (cursor.getColumnIndexOrThrow (DBStructure.YEAR));
            Events events = new Events (event,time,date,Month,Year);
            evensList.add(events);
        }
        cursor.close();
        dbOpenHelper.close();
    }
}
