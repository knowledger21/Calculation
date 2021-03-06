package jp.ac.hal;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;


public class MainActivity extends ActionBarActivity {

	TextView tv;
    TextView tv2;
    String calc ;//計算式格納用
    String str; //btnが押された場合格納用
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        calc = "";
        str = "";
        tv=(TextView)findViewById(R.id.result);
        tv2=(TextView)findViewById(R.id.result2);
        tv2.setText("");
        tv.setText("0");
        
        Button btn[] = new Button[17];
        btn[0] = (Button)findViewById(R.id.num1);
        btn[1] = (Button)findViewById(R.id.num2);
        btn[2] = (Button)findViewById(R.id.num3);
        btn[3] = (Button)findViewById(R.id.num4);
        btn[4] = (Button)findViewById(R.id.num5);
        btn[5] = (Button)findViewById(R.id.num6);
        btn[6] = (Button)findViewById(R.id.num7);
        btn[7] = (Button)findViewById(R.id.num8);
        btn[8] = (Button)findViewById(R.id.num9);
        btn[9] = (Button)findViewById(R.id.num0);
        btn[10] = (Button)findViewById(R.id.plus);
        btn[11] = (Button)findViewById(R.id.subtract);
        btn[12] = (Button)findViewById(R.id.multiply);
        btn[13] = (Button)findViewById(R.id.divide);
        btn[14] = (Button)findViewById(R.id.clear);
        btn[15] = (Button)findViewById(R.id.equal);
        btn[16] = (Button)findViewById(R.id.delete);
        
        for (int i = 0; i < 14; i++) {
            btn[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    btnClick(v);
                }
            });
        }

        btn[14].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clearClick();

            }
        });

        btn[15].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                equalClick();

            }
        });

        btn[16].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick();

            }
        });
    }
    
  //クリアが押された場合
    public void clearClick(){
        calc = "";
        str = "";
        tv.setText("0");
        tv2.setText("");
    }

    //deleteが押された場合(最後の一文字を消す)
    public void deleteClick(){
    	tv2.setText("");
        try{
            calc=calc.substring(0, calc.length()-1);
            tv.setText(calc);
            if(calc.length() == 0){
            	tv.setText("0");
            	//calc = "0";
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            tv.setText("0");
            return;
        }
        
        

    }

    //数値が押された場合
    public void btnClick(View v){
        Button btnStr=(Button) v;
        str = btnStr.getText().toString();
        //最初に0もしくは演算子後に0を入れた場合return
        if(str.equals("0") && (calc.equals("") || calc.endsWith("+") || calc.endsWith("-") || calc.endsWith("*") || calc.endsWith("/") )){
            return;
        }

        //最初に演算子を入れさせない
        if(calc.equals("") && ( str.endsWith("+") || str.endsWith("-") || str.endsWith("*") || str.endsWith("/") )){
            return;
        }

        //演算子のあとに演算子を入れさせない
        if( (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") ) && ( calc.endsWith("+") || calc.endsWith("-") || calc.endsWith("*") || calc.endsWith("/") )){
            return;
        }
        tv2.setText("");
        calc += str;
        tv.setText(calc);
    }


    //equalが押された場合
    public void equalClick(){

        /*calcの最後が演算子の場合return*/
        if(calc.endsWith("+") || calc.endsWith("-") || calc.endsWith("*") || calc.endsWith("/")){
            return;
        }

        //ArrayListに格納する
        ArrayList<String> caluList = new ArrayList<String>();//計算式格納用List
	    String calc_part ="" ;
	    int j = 0;
	    
	    for(int i = 0 ; i < calc.length(); i++){
	    	calc_part = String.valueOf(calc.charAt(i));
	    	
	    	//calc_partが演算子だったらそれまでの数値をひとまとめとしてlistに格納する
	           if(calc_part.equals("+") || calc_part.equals("-") || calc_part.equals("/") || calc_part.equals("*")){
	        	   String num = "";
	        	   //System.out.println("演算子が見つかったよ");
	        	   //String operator = "";
	        	   //数値を格納
	        	   //System.out.println(String.valueOf(calc.charAt(0)));
	        	   //System.out.println(j);
	        	   for(; j<i; j++){
	        		   //System.out.println(String.valueOf(calc.charAt(j)));
	        		   num += String.valueOf(calc.charAt(j));
	        		   //System.out.println(num);
	        	   }
	        	   caluList.add(num);
	        	   //演算子を格納
	        	   caluList.add(String.valueOf(calc.charAt(i)));
	        	   
	        	   //次の開始位置を見つかった演算子の次にする
	        	   j = i+1;
	           }
	           
	           //最後まで演算子が見つからなかったら
	           if(i == calc.length()-1){
	        	   String num = "";
	        	   for(; j < calc.length(); j++){
	        		   num += String.valueOf(calc.charAt(j));
	        	   }
	        	   caluList.add(num);
	           }
	    }
	    
	    Deque<String> operStack = new ArrayDeque<String>(); //数値及び演算子用スタック
		Deque<String> caluStack = new ArrayDeque<String>(); //一時的な演算子スタック
		Deque<String> polishStack = new ArrayDeque<String>(); //逆ポーランド方式にした計算式用スタック
		Deque<Double> ansStack = new ArrayDeque<Double>(); //演算結果用スタック
		
		for(int i = 0; i<caluList.size(); i++){
			//数値だったらcaluStackに格納
			if(!caluList.get(i).equals("+") && !caluList.get(i).equals("-") && !caluList.get(i).equals("*") && !caluList.get(i).equals("/")){ 
				//System.out.println(caluList.get(i));
				caluStack.push(caluList.get(i));
				
			//演算子の場合
			}else {
				//operStackに演算子が格納されていなかったらそのままoperStackに格納
				if(operStack.peek()==null){
					operStack.push(caluList.get(i));
				
				//operStackに演算子が格納されていた場合　読み込まれた演算子の優先度が高ければそのままoperStackに格納　
				//それ以外はoperStackに格納されてるものをcaluStackに格納し読み込んだ演算子をoperStackに格納
				}else {
					//関数を呼び出しスタックの演算子の優先度と読み込んだ演算子の優先度を決定する
					OperPriority Op = new OperPriority();
					int CaluOp01 = Op.OperPriority01(caluList.get(i));
					int CaluOp02 = Op.OperPriority02(operStack.peek());
					
					//読み込んだ演算子の優先度の方が高かったのでそのままoperStackに格納
					if(CaluOp01>CaluOp02){
						operStack.push(caluList.get(i));
					}else { //operStackに格納されてるものをcaluStackに格納し、読み込んだ演算子をoperStackに格納
						caluStack.push(operStack.poll());
						operStack.push(caluList.get(i));
					}
				}
			}
			
			if (i == caluList.size()-1) {
				for (String str: operStack) {
					caluStack.push(operStack.poll());
				}
			}
		
		}
		
		for (String str: caluStack) {
            polishStack.push(caluStack.poll());
		}
		
		//逆ポーランド方式から結果を求める
				Double num1 = (double) 0;//計算式用の変数
				Double num2 = (double) 0;//計算式用の変数
				String opreDelete = null;//使用した演算子を格納する変数
				
				for(String str:polishStack){
					System.out.println(str);
					switch (str) {
					case "+":
						num2 = (ansStack.poll());
						num1 = (ansStack.poll());
						ansStack.push(num1+num2);
						opreDelete = polishStack.poll();
						break;
					case "-":
						num2 = (ansStack.poll());
						num1 = (ansStack.poll());
						ansStack.push(num1-num2);
						opreDelete = polishStack.poll();
						break;
					case "/":
						num2 = (ansStack.poll());
						num1 = (ansStack.poll());
						ansStack.push(num1/num2);
						opreDelete = polishStack.poll();
						break;
					case "*":
						num2 = (ansStack.poll());
						num1 = (ansStack.poll());
						ansStack.push(num1*num2);
						opreDelete = polishStack.poll();
						break;
						
					default:
						ansStack.push(Double.parseDouble(polishStack.poll()));
						break;
					}
				}
				
				tv2.setText(calc);
				tv.setText(String.valueOf(ansStack.element()));
				calc = (String.valueOf(ansStack.element()));
				//最初に＝を押すとエラー
				
    }

    
    
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
