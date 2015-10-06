package com.censoMain.customControlls;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class CustomSpinnerAdapter<T> extends ArrayAdapter<T>{

    // Your custom values for the spinner (User)
    private T[] values;

    public CustomSpinnerAdapter(Context context, int textViewResourceId,
            T[] values) {
        super(context, textViewResourceId, values);
        this.values = values;
    }

    public int getCount(){
       return values.length;
    }

    public T getItem(int position){
       return values[position];
    }

    public long getItemId(int position){
       return position;
    }

    public int getIndex(T obj){

        int index = -1;

        for (int i=0;i<values.length;i++){
            if (this.getItem(i).equals(obj)){
                index = i;
            }
        }
        return index;
}
    
    
    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        //TextView label = new TextView(context);
        //label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        //label.setText(values[position].toString());

        // And finally return your dynamic (or custom) view for each spinner item
        //return label;
    	return super.getView(position, convertView, parent);
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
            ViewGroup parent) {
        //TextView label = new TextView(context);
        //label.setTextColor(Color.BLACK);
        //label.setText(values[position].toString());

        return super.getDropDownView(position, convertView, parent);
    }
}
