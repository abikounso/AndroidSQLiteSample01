package com.example.android.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Main extends Activity {

	private DatabaseHelper helper;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        helper = new DatabaseHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();
        
        Cursor c = db.query("capitals", new String[] {"prefecture"}, null, null, null, null, null);
        c.moveToFirst();
        
        CharSequence[] list = new CharSequence[c.getCount()];
        for (int i = 0; i < list.length; i++) {
        	list[i] = c.getString(0);
        	c.moveToNext();
        }
        c.close();
        
        Spinner spinner = (Spinner)findViewById(R.id.Spinner01);
        spinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, list));
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String prefecture = ((Spinner)parent).getSelectedItem().toString();
				Cursor c;
				
				c = db.query("capitals", new String[] {"capital"}, "prefecture='" + prefecture + "'", null, null, null, null);
				
				c.moveToFirst();
				String capital = c.getString(0);
				c.close();
				
				TextView textView = (TextView)findViewById(R.id.TextView01);
				textView.setText(capital);
				
				c = db.query("local_dishes", new String[] {"local_dish"}, "prefecture='" + prefecture + "'", null, null, null, null);
				
				c.moveToFirst();
				CharSequence[] list = new CharSequence[c.getCount()];
				for (int i = 0; i < list.length; i++) {
					list[i] = c.getString(0);
					c.moveToNext();
				}
				c.close();
				ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(Main.this, android.R.layout.simple_list_item_1, list);
				ListView listView = (ListView)findViewById(R.id.ListView01);
				listView.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        	
        });
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.close();
	}
	
	class DatabaseHelper extends SQLiteOpenHelper {

		private String[][] CAPITALS = {
			{"北海道", "札幌"},
			{"青森県", "青森"},
			{"岩手県", "盛岡"},
			{"宮城県", "仙台"},
			{"秋田県", "秋田"},
			{"山形県", "山形"},
			{"福島県", "福島"},
			{"茨城県", "水戸"},
			{"栃木県", "宇都宮"},
			{"群馬県", "前橋"},
			{"埼玉県", "さいたま"},
			{"千葉県", "千葉"},
			{"東京都", "新宿区"},
			{"神奈川県", "横浜"},
			{"新潟県", "新潟"},
			{"富山県", "富山"},
			{"石川県", "金沢"},
			{"福井県", "福井"},
			{"山梨県", "甲府"},
			{"長野県", "長野"},
			{"岐阜県", "岐阜"},
			{"静岡県", "静岡"},
			{"愛知県", "名古屋"},
			{"三重県", "津"},
			{"滋賀県", "大津"},
			{"京都府", "京都"},
			{"大阪府", "大阪"},
			{"兵庫県", "神戸"},
			{"奈良県", "奈良"},
			{"和歌山県", "和歌山"},
			{"鳥取県", "鳥取"},
			{"島根県", "松江"},
			{"岡山県", "岡山"},
			{"広島県", "広島"},
			{"山口県", "山口"},
			{"徳島県", "徳島"},
			{"香川県", "高松"},
			{"愛媛県", "松山"},
			{"高知県", "高知"},
			{"福岡県", "福岡"},
			{"佐賀県", "佐賀"},
			{"長崎県", "長崎"},
			{"熊本県", "熊本"},
			{"大分県", "大分"},
			{"宮崎県", "宮崎"},
			{"鹿児島県", "鹿児島"},
			{"沖縄県", "那覇"},
		};

		private String[][] LOCAL_DISHES = {
			{"三重県", "うざく"},
			{"三重県", "てこね寿司"},
			{"兵庫県", "そばめし"},
			{"兵庫県", "たこ飯"},
			{"北海道", "いかそうめん"},
			{"北海道", "いかのごろ煮"},
			{"北海道", "いももち"},
			{"北海道", "イカ飯"},
			{"北海道", "エスカロップ"},
			{"北海道", "ジンギスカン"},
			{"北海道", "チャンチャン焼き"},
			{"北海道", "チーズ入りいも団子"},
			{"北海道", "ルイベ"},
			{"北海道", "石狩鍋"},
			{"北海道", "豚丼"},
			{"千葉県", "かつおめし"},
			{"千葉県", "さやえんどうの卵とじ"},
			{"千葉県", "さんが焼き"},
			{"千葉県", "とろろ汁"},
			{"千葉県", "なめろう"},
			{"千葉県", "ピーナッツ味噌"},
			{"千葉県", "菜の花の辛子和え"},
			{"和歌山県", "梅びしお"},
			{"埼玉県", "ねぎぬた"},
			{"埼玉県", "ゼリーフライ"},
			{"大分県", "きらすまめし"},
			{"大分県", "そらきたもち"},
			{"大分県", "だんご汁"},
			{"大分県", "とり天"},
			{"大分県", "やせうま"},
			{"大分県", "鶏のから揚げ"},
			{"大阪府", "お好み焼き"},
			{"大阪府", "小田巻蒸し"},
			{"大阪府", "船場汁"},
			{"奈良県", "わらび餅"},
			{"奈良県", "茶粥"},
			{"奈良県", "飛鳥鍋"},
			{"宮城県", "おくずかけ"},
			{"宮城県", "ずんだ餅"},
			{"宮城県", "はらこ飯"},
			{"宮城県", "ばっけ味噌"},
			{"宮城県", "ほやの酢の物"},
			{"宮城県", "味噌しそ巻き"},
			{"宮城県", "山菜おこわ"},
			{"宮城県", "牛タン"},
			{"宮城県", "芋煮"},
			{"宮崎県", "ねりくり"},
			{"宮崎県", "チキン南蛮"},
			{"宮崎県", "冷や汁"},
			{"富山県", "ぶり大根"},
			{"富山県", "べっこう"},
			{"富山県", "やちゃら"},
			{"富山県", "イカの黒作り"},
			{"山口県", "けんちょう"},
			{"山口県", "瓦そば"},
			{"山形県", "山形のだし"},
			{"山形県", "枝豆ごはん"},
			{"山形県", "玉こんにゃく"},
			{"山形県", "納豆汁"},
			{"山形県", "芋煮"},
			{"山梨県", "ほうとう鍋"},
			{"岩手県", "くるみじょうゆ餅"},
			{"岩手県", "もち料理"},
			{"岩手県", "冷麺"},
			{"岩手県", "芋煮"},
			{"岩手県", "茎わかめのポン酢しょうゆ"},
			{"岩手県", "豆腐田楽"},
			{"広島県", "かきのしぐれ煮"},
			{"広島県", "牡蠣の土手鍋"},
			{"愛媛県", "しょうゆ飯"},
			{"愛媛県", "ひゅうが飯"},
			{"愛媛県", "鯛めし"},
			{"愛知県", "ひつまぶし "},
			{"愛知県", "味噌カツ"},
			{"愛知県", "味噌煮込みうどん"},
			{"愛知県", "手羽先の唐揚"},
			{"新潟県", "いもこみそ"},
			{"新潟県", "きゃらぶき"},
			{"新潟県", "けんさ焼き"},
			{"新潟県", "大根のきんぴら"},
			{"新潟県", "小豆がゆ"},
			{"新潟県", "鮭の焼付け"},
			{"東京都", "ねぎま鍋"},
			{"東京都", "深川飯"},
			{"栃木県", "いとこ煮"},
			{"栃木県", "いもフライ"},
			{"栃木県", "かんぴょうの卵とじ"},
			{"栃木県", "大根そば"},
			{"栃木県", "法度汁"},
			{"栃木県", "芋ちゃのこ"},
			{"栃木県", "鶏つくねと若竹煮"},
			{"沖縄県", "ちんびん"},
			{"沖縄県", "にんじんしりしり"},
			{"沖縄県", "ククメシ"},
			{"沖縄県", "ゴーヤチャンプルー"},
			{"沖縄県", "ソーミンチャンプルー"},
			{"沖縄県", "ヒラヤーチ"},
			{"沖縄県", "ミヌダル"},
			{"熊本県", "高菜めし"},
			{"石川県", "なすそうめん"},
			{"石川県", "雪見鍋"},
			{"神奈川県", "しらす丼"},
			{"福岡県", "がめ煮"},
			{"福岡県", "焼きうどん"},
			{"福島県", "イカ人参"},
			{"福島県", "切りこぶと油揚げの煮物"},
			{"福島県", "芋煮"},
			{"秋田県", "きりたんぽ"},
			{"秋田県", "芋煮"},
			{"群馬県", "こんにゃくのきんぴら"},
			{"群馬県", "大根そば"},
			{"茨城県", "そぼろ納豆"},
			{"茨城県", "アンコウのとも酢"},
			{"長崎県", "ヒカド"},
			{"長野県", "おやき"},
			{"長野県", "にらせんべい"},
			{"長野県", "やたら"},
			{"青森県", "けの汁"},
			{"青森県", "たまごみそ"},
			{"青森県", "ほたての貝焼き味噌"},
			{"青森県", "焼しじみ"},
			{"青森県", "芋煮"},
			{"香川県", "たくあんのきんぴら"},
			{"香川県", "イリコ飯"},
			{"高知県", "茄子のたたき"},
			{"高知県", "鰹の土佐造り"},
			{"鳥取県", "ののこ飯"},
			{"鹿児島", "春駒"},
			{"鹿児島県", "さつまいもご飯"},
			{"鹿児島県", "さつま汁"},
			{"鹿児島県", "にがごいの油炒め"},
			{"鹿児島県", "油そうめん"},
			{"鹿児島県", "鶏飯"},
		};		

		public DatabaseHelper(Context context) {
			super(context, null, null, 1);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.beginTransaction();
			try {
				SQLiteStatement stmt;
				db.execSQL("create table capitals (prefecture text primary key, capital text not null);");
				stmt = db.compileStatement("insert into capitals values (?, ?);");
				
				for (String[] capital : CAPITALS) {
					stmt.bindString(1, capital[0]);
					stmt.bindString(2, capital[1]);
					stmt.executeInsert();
				}
				
				db.execSQL("create table local_dishes (prefecture text not null, local_dish text not null);");
				stmt = db.compileStatement("insert into local_dishes values (?, ?);");
				
				for (String[] localDish : LOCAL_DISHES) {
					stmt.bindString(1, localDish[0]);
					stmt.bindString(2, localDish[1]);
					stmt.executeInsert();
				}
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
		
	}
}