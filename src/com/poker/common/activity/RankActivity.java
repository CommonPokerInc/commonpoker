package com.poker.common.activity;

import java.util.ArrayList;
import java.util.List;

import com.poker.common.R;
import com.poker.common.R.layout;
import com.poker.common.R.menu;
import com.poker.common.entity.AbsPlayer;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.util.ScreenShot;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RankActivity extends Activity implements OnClickListener {

	private Intent intent;
	private RankAdapter rankAdapter;
	private ListView rankList;
	private int index;
	private List<ClientPlayer> players;
	private ImageView snapshot, close;
	private boolean isScreenShot = false;
	private int[] rankImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		setContentView(R.layout.activity_rank);

		initData();
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		rankList = (ListView) findViewById(R.id.rank_list);
		// rankAdapter = new RankAdapter(getApplicationContext(),players,index,rankImg);
		snapshot = (ImageView) findViewById(R.id.snapshot_img);
		close = (ImageView) findViewById(R.id.close_img);

		snapshot.setOnClickListener(this);
		close.setOnClickListener(this);
	}
	
	private void initRankImg(){
		rankImg = new int[]{R.drawable.no1,R.drawable.no2,R.drawable.no3,R.drawable.no4,R.drawable.no5,R.drawable.no6};
	}

	private void initData() {
		// TODO Auto-generated method stub
		/*
		 * intent = getIntent(); players = new ArrayList<AbsPlayer>(); players =
		 * intent.getParcelableArrayListExtra("rankList"); index =
		 * intent.getIntExtra("index", 0);
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rank, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.snapshot_img:
			isScreenShot = ScreenShot.shoot(RankActivity.this);
			if (isScreenShot) {
				Toast.makeText(getApplicationContext(), "截图保存成功！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "截图保存不成功！",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.close_img:
			finish();
			break;

		default:
			break;
		}
	}

	private boolean sreenShot() {
		// TODO Auto-generated method stub
		
		return true;
	}

	class RankAdapter extends BaseAdapter {

		private Context context;
		private List<ClientPlayer> list = new ArrayList<ClientPlayer>();
		private int isme;
		private int[] rankImage;

		public RankAdapter(Context context, List<ClientPlayer> list, int indext,int[] rankImg) {
			this.context = context;
			this.list = list;
			this.isme = index;
			this.rankImage = rankImg;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.rank_each, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.rank_player_name_txt);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.rank_which_img);
				holder.money = (TextView) convertView
						.findViewById(R.id.rank_player_money_txt);
				holder.rank_each_layout = (RelativeLayout)convertView.findViewById(R.id.rank_each_layout);
				holder.line = (ImageView)convertView.findViewById(R.id.line);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(list.get(position).getInfo().getName());
			holder.icon.setImageResource(rankImage[position]);
			holder.money.setText("");
			if(position == index){
				holder.rank_each_layout.setBackgroundResource(R.drawable.me);
			}
			if(position == list.size() -1){
				holder.line.setImageResource(R.drawable.line);
			}
			return convertView;
		}
	}

	static class ViewHolder {
		TextView name;
		ImageView icon;
		TextView money;
		RelativeLayout rank_each_layout;
		ImageView line;
	}

}
