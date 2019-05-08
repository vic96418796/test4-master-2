package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.Intent;


public class search_label extends AppCompatActivity {
    private String[] Taiwan = new String[]{"基隆","台北","新北","桃園","新竹","苗栗","台中","南投","彰化","雲林","嘉義","台南","高雄","屏東","台東","花蓮","宜蘭",
            "澎湖","金門","連江"};
    private String[] zone_基隆 = new String[]{"仁愛區","中正區","信義區","中山區","安樂區","暖暖區","七堵區"};
    private String[] zone_台北 = new String[]{"中正區","大同區","中山區","松山區","大安區","萬華區","信義區","士林區","北投區","內湖區","南港區","文山區"};
    private String[] zone_新北 = new String[]{"板橋區","新莊區","中和區","永和區","土城區","樹林區","三峽區","鶯歌區","三重區","蘆洲區","五股區","泰山區","林口區",
            "八里區","淡水區","三芝區","石門區","金山區","萬里區","汐止區","瑞芳區","貢寮區","平溪區","雙溪區","新店區","深坑區","石碇區","坪林區","烏來區"};
    private String[] zone_桃園 = new String[]{"桃園區","中壢區","平鎮區","八德區","楊梅區","蘆竹區","大溪區","龍潭區","龜山區","大園區","觀音區","新屋區","復興區"};
    private String[] zone_新竹 = new String[]{"東區","北區","香山區","竹北市","竹東鎮","新埔鎮","關西鎮","湖口鄉","新豐鄉","峨眉鄉","寶山鄉","北埔鄉","芎林鄉",
            "橫山鄉","尖石鄉","五峰鄉"};
    private String[] zone_苗栗 = new String[]{"苗栗市","頭份市","竹南鎮","後龍鎮","通霄鎮","苑裡鎮","卓蘭鎮","造橋鄉","西湖鄉","頭屋鄉","公館鄉","銅鑼鄉",
            "三義鄉","大湖鄉","獅潭鄉","三灣鄉", "南庄鄉","泰安鄉"};
    private String[] zone_台中 = new String[]{"中區","東區","南區","西區","北區","北屯區","西屯區","南屯區","太平區","大里區","霧峰區","烏日區","豐原區",
            "后里區","石岡區","東勢區","新社區","潭子區","大雅區","神岡區","大肚區","沙鹿區","龍井區","梧棲區","清水區","大甲區","外埔區","大安區","和平區"};
    private String[] zone_南投 = new String[]{"南投市","埔里鎮","草屯鎮","竹山鎮","集集鎮","名間鄉","鹿谷鄉","中寮鄉","魚池鄉","國姓鄉","水里鄉","信義鄉","仁愛鄉"};
    private String[] zone_彰化 = new String[]{"彰化市","員林市","和美鎮","鹿港鎮","溪湖鎮","二林鎮","田中鎮","北斗鎮","花壇鄉","芬園鄉","大村鄉","永靖鄉","伸港鄉",
            "線西鄉","福興鄉","秀水鄉","埔心鄉","埔鹽鄉","大城鄉","芳苑鄉","竹塘鄉","社頭鄉","二水鄉","田尾鄉","埤頭鄉","溪州鄉"};
    private String[] zone_雲林 = new String[]{"斗六市","斗南鎮","虎尾鎮","西螺鎮","土庫鎮","北港鎮","林內鄉","古坑鄉","大埤鄉","莿桐鄉","褒忠鄉","二崙鄉","崙背鄉",
            "麥寮鄉","臺西鄉","東勢鄉","元長鄉","四湖鄉","口湖鄉","水林鄉"};
    private String[] zone_嘉義 = new String[]{"太保市","朴子市","布袋鎮","大林鎮","民雄鄉","溪口鄉","新港鄉","六腳鄉","東石鄉","義竹鄉","鹿草鄉","水上鄉","中埔鄉",
            "竹崎鄉","梅山鄉","番路鄉","大埔鄉","阿里山鄉"};
    private String[] zone_台南 = new String[]{"中西區","東區","南區","北區","安平區","安南區","永康區","歸仁區","新化區","左鎮區","玉井區","楠西區","南化區","仁德區",
            "關廟區","龍崎區","官田區","麻豆區","佳里區","西港區","七股區","將軍區","學甲區","北門區","新營區","後壁區","白河區","東山區","六甲區","下營區","柳營區","鹽水區",
            "善化區","大內區","山上區","新市區","安定區"};
    private String[] zone_高雄 = new String[]{"楠梓區","左營區","鼓山區","三民區","鹽埕區","前金區","新興區","苓雅區","前鎮區","旗津區","小港區","鳳山區","大寮區",
            "鳥松區","林園區","仁武區","大樹區","大社區","岡山區","路竹區","橋頭區","梓官區","彌陀區","永安區","燕巢區","田寮區","阿蓮區","茄萣區","湖內區","旗山區",
            "美濃區","內門區","杉林區","甲仙區","六龜區","茂林區","桃源區","那瑪夏區"};
    private String[] zone_屏東 = new String[]{"屏東市","潮州鎮","東港鎮","恆春鎮","萬丹鄉","長治鄉","麟洛鄉","九如鄉","里港鄉","鹽埔鄉","高樹鄉","萬巒鄉","內埔鄉",
            "竹田鄉","新埤鄉","枋寮鄉","新園鄉","崁頂鄉","林邊鄉","南州鄉","佳冬鄉","琉球鄉","車城鄉","滿州鄉","枋山鄉","霧臺鄉","瑪家鄉","泰武鄉","來義鄉","春日鄉",
            "獅子鄉","牡丹鄉","三地門鄉"};
    private String[] zone_台東 = new String[]{"臺東市","成功鎮","關山鎮","長濱鄉","池上鄉","東河鄉","鹿野鄉","卑南鄉","大武鄉","綠島鄉","太麻里鄉","海端鄉","延平鄉",
            "金峰鄉","達仁鄉","蘭嶼鄉"};
    private String[] zone_花蓮 = new String[]{"花蓮市","鳳林鎮","玉里鎮","新城鄉","吉安鄉","壽豐鄉","光復鄉","豐濱鄉","瑞穗鄉","富里鄉","秀林鄉","萬榮鄉","卓溪鄉"};
    private String[] zone_宜蘭 = new String[]{"宜蘭市","頭城鎮","羅東鎮","蘇澳鎮","礁溪鄉","壯圍鄉","員山鄉","冬山鄉","五結鄉","三星鄉","大同鄉","南澳鄉"};
    private String[] zone_澎湖 = new String[]{"馬公市","湖西鄉","白沙鄉","西嶼鄉","望安鄉","七美鄉"};
    private String[] zone_金門 = new String[]{"金城鎮","金湖鎮","金沙鎮","金寧鄉","烈嶼鄉","烏坵鄉"};
    private String[] zone_馬祖 = new String[]{"南竿鄉","北竿鄉","莒光鄉","東引鄉"};
    private String[][] Taiwan_zone = new String[][]{{"仁愛區","中正區","信義區","中山區","安樂區","暖暖區","七堵區"},
            {"中正區","大同區","中山區","松山區","大安區","萬華區","信義區","士林區","北投區","內湖區","南港區","文山區"},
            {"板橋區","新莊區","中和區","永和區","土城區","樹林區","三峽區","鶯歌區","三重區","蘆洲區","五股區","泰山區","林口區",
                    "八里區","淡水區","三芝區","石門區","金山區","萬里區","汐止區","瑞芳區","貢寮區","平溪區","雙溪區","新店區","深坑區","石碇區","坪林區","烏來區"},
            {"桃園區","中壢區","平鎮區","八德區","楊梅區","蘆竹區","大溪區","龍潭區","龜山區","大園區","觀音區","新屋區","復興區"},
            {"東區","北區","香山區","竹北市","竹東鎮","新埔鎮","關西鎮","湖口鄉","新豐鄉","峨眉鄉","寶山鄉","北埔鄉","芎林鄉",
                    "橫山鄉","尖石鄉","五峰鄉"},
            {"苗栗市","頭份市","竹南鎮","後龍鎮","通霄鎮","苑裡鎮","卓蘭鎮","造橋鄉","西湖鄉","頭屋鄉","公館鄉","銅鑼鄉",
                    "三義鄉","大湖鄉","獅潭鄉","三灣鄉", "南庄鄉","泰安鄉"},
            {"中區","東區","南區","西區","北區","北屯區","西屯區","南屯區","太平區","大里區","霧峰區","烏日區","豐原區",
                    "后里區","石岡區","東勢區","新社區","潭子區","大雅區","神岡區","大肚區","沙鹿區","龍井區","梧棲區","清水區","大甲區","外埔區","大安區","和平區"},
            {"南投市","埔里鎮","草屯鎮","竹山鎮","集集鎮","名間鄉","鹿谷鄉","中寮鄉","魚池鄉","國姓鄉","水里鄉","信義鄉","仁愛鄉"},
            {"彰化市","員林市","和美鎮","鹿港鎮","溪湖鎮","二林鎮","田中鎮","北斗鎮","花壇鄉","芬園鄉","大村鄉","永靖鄉","伸港鄉",
                    "線西鄉","福興鄉","秀水鄉","埔心鄉","埔鹽鄉","大城鄉","芳苑鄉","竹塘鄉","社頭鄉","二水鄉","田尾鄉","埤頭鄉","溪州鄉"},
            {"斗六市","斗南鎮","虎尾鎮","西螺鎮","土庫鎮","北港鎮","林內鄉","古坑鄉","大埤鄉","莿桐鄉","褒忠鄉","二崙鄉","崙背鄉",
                    "麥寮鄉","臺西鄉","東勢鄉","元長鄉","四湖鄉","口湖鄉","水林鄉"},
            {"太保市","朴子市","布袋鎮","大林鎮","民雄鄉","溪口鄉","新港鄉","六腳鄉","東石鄉","義竹鄉","鹿草鄉","水上鄉","中埔鄉",
                    "竹崎鄉","梅山鄉","番路鄉","大埔鄉","阿里山鄉"},
            {"中西區","東區","南區","北區","安平區","安南區","永康區","歸仁區","新化區","左鎮區","玉井區","楠西區","南化區","仁德區",
                    "關廟區","龍崎區","官田區","麻豆區","佳里區","西港區","七股區","將軍區","學甲區","北門區","新營區","後壁區","白河區","東山區","六甲區","下營區","柳營區","鹽水區",
                    "善化區","大內區","山上區","新市區","安定區"},
            {"楠梓區","左營區","鼓山區","三民區","鹽埕區","前金區","新興區","苓雅區","前鎮區","旗津區","小港區","鳳山區","大寮區",
                    "鳥松區","林園區","仁武區","大樹區","大社區","岡山區","路竹區","橋頭區","梓官區","彌陀區","永安區","燕巢區","田寮區","阿蓮區","茄萣區","湖內區","旗山區",
                    "美濃區","內門區","杉林區","甲仙區","六龜區","茂林區","桃源區","那瑪夏區"},
            {"屏東市","潮州鎮","東港鎮","恆春鎮","萬丹鄉","長治鄉","麟洛鄉","九如鄉","里港鄉","鹽埔鄉","高樹鄉","萬巒鄉","內埔鄉",
                    "竹田鄉","新埤鄉","枋寮鄉","新園鄉","崁頂鄉","林邊鄉","南州鄉","佳冬鄉","琉球鄉","車城鄉","滿州鄉","枋山鄉","霧臺鄉","瑪家鄉","泰武鄉","來義鄉","春日鄉",
                    "獅子鄉","牡丹鄉","三地門鄉"},
            {"臺東市","成功鎮","關山鎮","長濱鄉","池上鄉","東河鄉","鹿野鄉","卑南鄉","大武鄉","綠島鄉","太麻里鄉","海端鄉","延平鄉",
                    "金峰鄉","達仁鄉","蘭嶼鄉"},
            {"花蓮市","鳳林鎮","玉里鎮","新城鄉","吉安鄉","壽豐鄉","光復鄉","豐濱鄉","瑞穗鄉","富里鄉","秀林鄉","萬榮鄉","卓溪鄉"},
            {"宜蘭市","頭城鎮","羅東鎮","蘇澳鎮","礁溪鄉","壯圍鄉","員山鄉","冬山鄉","五結鄉","三星鄉","大同鄉","南澳鄉"},
            {"馬公市","湖西鄉","白沙鄉","西嶼鄉","望安鄉","七美鄉"},
            {"金城鎮","金湖鎮","金沙鎮","金寧鄉","烈嶼鄉","烏坵鄉"},
            {"南竿鄉","北竿鄉","莒光鄉","東引鄉"},
            {"台北","新北","桃園","新竹","苗栗","台中","彰化","雲林","嘉義","台南","高雄","屏東","台東","花蓮","宜蘭"}};
    private Spinner spinner1;
    private Spinner spinner2;
    private Context context;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    int a=0;
    int b=0;
    int c=0;
    int d=0;
    int e=0;
    int f=0;
    int g=0;
    int h=0;
    int i=0;
    int j=0;
    int k=0;
    int l=0;
    int m=0;
    int n=0;
    int o=0;

    private OnItemSelectedListener selectListner = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int pos = spinner1.getSelectedItemPosition();
            adapter2 = new ArrayAdapter<String>(context,R.layout.myspinner,Taiwan_zone[pos]);
            spinner2.setAdapter(adapter2);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_label);


        final Button animal = findViewById(R.id.animal);
        if ( f == 0 ) {
            animal.setSelected(false); }
        else {
            animal.setSelected(true); }
        animal.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( f == 0 ) {
                    animal.setSelected(true);
                    f++;
                }
                else {
                    animal.setSelected(false);
                    f--;
                }
            }
        });
        final Button no_time_limit = findViewById(R.id.no_time_limit);
        if ( g == 0 ) {
            no_time_limit.setSelected(false); }
        else {
            no_time_limit.setSelected(true); }
        no_time_limit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( g == 0 ) {
                    no_time_limit.setSelected(true);
                    g++;
                }
                else {
                    no_time_limit.setSelected(false);
                    g--;
                }
            }
        });
        final Button wifi = findViewById(R.id.wifi);
        if ( h == 0 ) {
            wifi.setSelected(false); }
        else {
            wifi.setSelected(true); }
        wifi.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( h == 0 ) {
                    wifi.setSelected(true);
                    h++;
                }
                else {
                    wifi.setSelected(false);
                    h--;
                }
            }
        });
        final Button socket = findViewById(R.id.socket);
        if ( i == 0 ) {
            socket.setSelected(false); }
        else {
            socket.setSelected(true); }
        socket.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( i == 0 ) {
                    socket.setSelected(true);
                    i++;
                }
                else {
                    socket.setSelected(false);
                    i--;
                }
            }
        });
        final Button brunch = findViewById(R.id.brunch);
        if ( j == 0 ) {
            brunch.setSelected(false); }
        else {
            brunch.setSelected(true); }
        brunch.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( j == 0 ) {
                    brunch.setSelected(true);
                    j++;
                }
                else {
                    brunch.setSelected(false);
                    j--;
                }
            }
        });

        final Button aftertea = findViewById(R.id.aftertea);
        if ( l == 0 ) {
            aftertea.setSelected(false); }
        else {
            aftertea.setSelected(true); }
        aftertea.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( l == 0 ) {
                    aftertea.setSelected(true);
                    l++;
                }
                else {
                    aftertea.setSelected(false);
                    l--;
                }
            }
        });

        final Button pay_limit = findViewById(R.id.pay_limit);
        if ( n == 0 ) {
            pay_limit.setSelected(false); }
        else {
            pay_limit.setSelected(true); }
        pay_limit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( n == 0 ) {
                    pay_limit.setSelected(true);
                    n++;
                }
                else {
                    pay_limit.setSelected(false);
                    n--;
                }
            }
        });
        final Button dessert = findViewById(R.id.dessert);
        if ( o == 0 ) {
            dessert.setSelected(false); }
        else {
            dessert.setSelected(true); }
        dessert.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( o == 0 ) {
                    dessert.setSelected(true);
                    o++;
                }
                else {
                    dessert.setSelected(false);
                    o--;
                }
            }
        });

        Button clean_all = findViewById(R.id.clean_all);

        clean_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animal.setSelected(false);
                no_time_limit.setSelected(false);
                wifi.setSelected(false);
                socket.setSelected(false);
                brunch.setSelected(false);

                aftertea.setSelected(false);

                pay_limit.setSelected(false);
                dessert.setSelected(false);
            }
        });



        context = this;

        adapter = new ArrayAdapter<String>(this,R.layout.myspinner, Taiwan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(selectListner);

        adapter2 = new ArrayAdapter<String>(this,R.layout.myspinner, zone_台北);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);


    }
}
