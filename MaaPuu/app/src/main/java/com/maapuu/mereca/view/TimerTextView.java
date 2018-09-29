package com.maapuu.mereca.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.maapuu.mereca.adapter.PersonalHomepageDtRecyclerAdapter;

public class TimerTextView extends AppCompatTextView implements Runnable {
	// 时间变量
	private int day, hour, minute, second;
	// 当前计时器是否运行
	private boolean isRun = false;
	private CallBack callBack = null;
	private int position = -1;

	public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public TimerTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimerTextView(Context context) {
		super(context);
	}

	public static interface CallBack {
		void onCall();
		void onCall(int position);
	}

	public void setCall(CallBack callBack) {
		this.callBack = callBack;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * 将倒计时时间毫秒数转换为自身变量
	 * 
	 * @param time
	 *            时间间隔毫秒数
	 */
	public void setTimes(long time) {
		//将毫秒数转化为时间
				this.second = (int) (time / 1000) % 60;
				this.minute = (int) (time / (60 * 1000) % 60);
				this.hour = (int) (time / (60 * 60 * 1000) % 24);
				this.day = (int) (time / (24 * 60 * 60 * 1000));
	}

	/**
	 * 显示当前时间
	 * 
	 * @return
	 */
	public String showTime() {
		StringBuilder time = new StringBuilder();
//		time.append(day);
//		time.append("天");
		if(day > 0){
			if(day>9){
				time.append(day);
			}else {
				time.append("0" + day);
			}
			time.append("天");
		}
		if((hour) >9){
			time.append(hour);
		}else {
			time.append("0" + hour);
		}
//		if((hour + day *24) >9){
//			time.append((hour + day *24));
//		}else {
//			time.append("0" + (hour + day *24));
//		}
		time.append(":");
		if(minute>9){
			time.append(minute);
		}else {
			time.append("0" + minute);
		}
		time.append(":");
		if(second>9){
			time.append(second);
		}else {
			time.append("0" + second);
		}
//		time.append(":");
		return time.toString();
	}

	/**
	 * 实现倒计时
	 */
	private void countdown() {
		if (second == 0) {
			if (minute == 0) {
				if (hour == 0) {
					if (day == 0) {
						//当时间归零时停止倒计时
						isRun = false;
						return;
					} else {
						day--;
					}
					hour = 23;
				} else {
					hour--;
				}
				minute = 59;
			} else {
				minute--;
			}
			second = 60;
		}

		second--;
	}

	public boolean isRun() {
		return isRun;
	}

	/**
	 * 开始计时
	 */
	public void start() {
		isRun = true;
		run();
	}

	/**
	 * 结束计时
	 */
	public void stop() {
		isRun = false;
	}

	/**
	 * 实现计时循环
	 */
	@Override
	public void run() {
		if (isRun) {
			// Log.d(TAG, "Run");
			countdown();
			this.setText(showTime());
			postDelayed(this, 1000);
		} else {
			if(callBack != null){
				if(position != -1){
					callBack.onCall(position);
				}else {
					callBack.onCall();
				}
			}
			removeCallbacks(this);
		}
	}

}
