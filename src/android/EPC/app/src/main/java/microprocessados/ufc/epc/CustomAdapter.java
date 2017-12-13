package microprocessados.ufc.epc;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Simao on 12/12/2017.
 */

public class CustomAdapter extends ArrayAdapter<BluetoothDevice> implements View.OnClickListener{

    private ArrayList<BluetoothDevice> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtAdress;
//        TextView txtVersion;
//        ImageView info;
    }

    public CustomAdapter(Context context,int layout_id,ArrayList<BluetoothDevice> data) {
        super(context, layout_id, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        BluetoothDevice BluetoothDevice=(BluetoothDevice)object;

        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Nome " +BluetoothDevice.getName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BluetoothDevice BluetoothDevice = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtAdress = (TextView) convertView.findViewById(R.id.adress);
//            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
//            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;

        viewHolder.txtName.setText(BluetoothDevice.getName());
//        viewHolder.txtType.setText(BluetoothDevice.getType());
        viewHolder.txtAdress.setText(BluetoothDevice.getAddress());
//        viewHolder.info.setOnClickListener(this);
//        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}