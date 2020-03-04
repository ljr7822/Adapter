package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 适配RecyclerView实例的一个类。其作用是将子项(这里指每一个msg_item.layout)与RecyclerView的一个布局适配，这个是重点。
 * 类MsgAdapter继承于RecyclerView.Adapter，并将泛型指定为自定义的内部类ViewHolder；
 * 继承自类RecyclerView.Adapter后重写的几个方法；
 *
 * @author iwenLjr
 * Create to 2020/3/3 10:45
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> mMsgList;

    /**
     * 构造函数MsgAdapter,在创建这个适配器对象的时候，将所有数据都传入，以便进行之后的操作。
     */
    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }

    /**
     * ①在实际开发种 LayoutInflater 这个类还是非常有用的，它的作用类似于 findViewById()，
     *   不同点是 LayoutInflater 是用来找 layout 下x ml布局文件，并且实例化！
     *   而 findViewById() 是找具体 xml 下的具体 widget控件 (如:Button,TextView等)。
     *
     * ②对于一个没有被载入或者想要动态载入的界面，都需要使用 inflate 来载入。
     * ③我们要知道,什么是已经被载入的 layout ,什么是还没有载入的?
     *   我们启动一个应用，与入口 Activity 相关的 layout {常见的是main.xml} 就是被载入的，即在 Oncreate() 中的。
     *   对于一个已经载入的 Activity,，就可以使用实现了这个 Activiyt 的 findViewById 方法来获得其中的界面元素。
     *   而对于其它没有被载入的 layout，就要动态载入了或通过另一个 activity。
     *
     * 这个 item 需要我们用 inflate 函数把 msg_item 动态的加载进main布局,
     * 并且返回了一个用来获取 item 里控件并且对其进行操作的 View 对象。
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    /**
     * 对控件有约束的控制,意思是通过判断信息类型来决定显示或者隐藏哪个布局。
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Msg msg = mMsgList.get(position);

        if(msg.getType() == Msg.TYPE_RECEICED){
            // 如果是接收消息
            holder.leftLayout.setVisibility(View.VISIBLE); // 左边对话框出现
            holder.rightLayout.setVisibility(View.GONE); // 右边对话框隐藏
            holder.leftMsg.setText(msg.getContent());
        }else if(msg.getType() == Msg.TYPE_SEND){
            // 如果是发送消息
            holder.rightLayout.setVisibility(View.VISIBLE);// 右边对话框出现
            holder.leftLayout.setVisibility(View.GONE); // 左边对话框隐藏
            holder.rightMsg.setText(msg.getContent());
        }
    }

    /**
     * 利用创建时传入的数据，获取列表里总共有多少个Item(项目)。
     * 对于这个函数的作用，我的理解是返回能被布局的总的Item的数量。
     * 至于返回这个数据有什么作用，我们就不必深究了，
     * 系统会自动调用这个函数来获得它需要的数据。
     * @return 列表里总共有多少个Item
     */
    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    /**
     * 内部类ViewHolder
     *
     * 我将内部类ViewHolder理解为视图控件持有类，
     * 是一个囊括本类对象里所有控件的容器，
     * 本类的作用也是为了方便在后面不用重复去定义这些控件.
     *
     * ①它继承于RecyclerView.ViewHolder类，这与外层MsgAdapter类相似。
     *
     * ②ViewHolder 类还在构造函数里调用了父类的构造函数，并且为每一个Item里的所有控件都创建了一个对应的对象。
     *
     * 由此，ViewHolder 类创建的对象就能够对Item里面的控件进行操作了。
     *
     * 这里你可能会有疑问，构造函数中的参数是哪里来的，系统怎么知道需要哪个Item？
     *
     * 这个不用担心，这些系统会自动帮我们做，把传入的List<Msg>对象一个个遍历，单独地对每一个对象进行操作。
     */
    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;

        public ViewHolder(@NonNull View view) {
            super(view);
            leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
        }
    }

}
