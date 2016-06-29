import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.abs.samih.fcm_ex01.MyTask;
import com.abs.samih.fcm_ex01.R;

import java.util.zip.Inflater;

/**
 * Created by user on 27/06/2016.
 */
public class MyAdapterTask extends ArrayAdapter<MyTask> {

    public MyAdapterTask(Context context, int resource) {
        super(context, resource);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



//        if (convertView==null)
//            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_my_task, parent,false);
        CheckBox chbIsCompleted = (CheckBox)convertView.findViewById(R.id.chbxIsComplated);

        TextView tvText = (TextView)convertView.findViewById(R.id.tvText);
        TextView tvDate = (TextView)convertView.findViewById(R.id.tvDate);
        ImageButton btnCall = (ImageButton)convertView.findViewById(R.id.btnCall);
        ImageButton btnAdd = (ImageButton)convertView.findViewById(R.id.btnLocation);
        ImageButton btnLocation = (ImageButton)convertView.findViewById(R.id.btnLocation);

        MyTask myTask = getItem(position);
        chbIsCompleted.setChecked(myTask.isComplated());
        tvText.setText(myTask.getText());
        tvDate.setText(myTask.getText());
        btnCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return super.getView(position, convertView, parent);
    }
}
