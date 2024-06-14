package com.manager.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.manager.appbanhang.Interface.ItemClickListener;
import com.manager.appbanhang.R;
import com.manager.appbanhang.model.DonHang;
import com.manager.appbanhang.model.EventBus.DonHangEvent;
import com.manager.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> listdonhang;

    public DonHangAdapter(Context context, List<DonHang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = listdonhang.get(position);
        holder.txtdonhang.setText("Đơn hàng số : " + donHang.getId());
        holder.txtdiachi.setText("Địa chỉ giao hàng : " + donHang.getDiachi());
        holder.txtsdt.setText("SĐT : " + donHang.getSdt());
        holder.trangthai.setText(trangThaiDon(donHang.getTrangthai()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tongtien.setText("Tổng Tiền : " + decimalFormat.format(Double.parseDouble(donHang.getTongtien())) + " đ");
        holder.txtusername.setText("Tên người đặt : " + donHang.getTenuser());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rechitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());

        ChiTieTDonHangAdapter chiTieTDonHangAdapter = new ChiTieTDonHangAdapter(context,donHang.getItem());
        holder.rechitiet.setLayoutManager(layoutManager);
        holder.rechitiet.setAdapter(chiTieTDonHangAdapter);
        holder.rechitiet.setRecycledViewPool(viewPool);
        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (isLongClick) {
                    EventBus.getDefault().postSticky (new DonHangEvent (donHang));
                }
            }
        });
    }
    private String trangThaiDon(int status) {
        String result = "";
        switch (status) {
            case 0:
                result = "Đơn hàng đang được xử lí";
                break;
            case 1:
                result = "Đơn hàng đã được chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã được giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Giao hàng thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtdonhang, trangthai, tongtien, txtdiachi, txtsdt, txtusername;
        RecyclerView rechitiet;
        ItemClickListener listener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            rechitiet = itemView.findViewById(R.id.recycleview_chitiet);
            trangthai = itemView.findViewById(R.id.trangthai);
            tongtien = itemView.findViewById(R.id.donhang_tongtien);
            txtdiachi = itemView.findViewById(R.id.diachi);
            txtsdt = itemView.findViewById(R.id.sdt);
            txtusername = itemView.findViewById(R.id.donhang_tenuser);
            itemView.setOnLongClickListener(this);
        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onClick(view, getAdapterPosition(), true);
            return false;
        }
    }
}
