package cs2901.utec.chat_mobile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

public class Contacts extends RecyclerView.Adapter<Contacts.MyViewHolder>{
    private List<JSONObject> contactslist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contact_name;

        public MyViewHolder(View view) {
            super(view);
            contact_name = (TextView) view.findViewById(R.id.contact_name);
        }
    }

    public Contacts(List<JSONObject> contactslist) {
        this.contactslist = contactslist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        JSONObject contact = contactslist.get(position);
        try {
            holder.contact_name.setText(contact.getString("username"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return contactslist.size();
    }
}
