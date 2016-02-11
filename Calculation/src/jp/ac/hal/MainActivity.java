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
    String calc ;//ŒvZ®Ši”[—p
    String str; //btn‚ª‰Ÿ‚³‚ê‚½ê‡Ši”[—p
	
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
    
  //ƒNƒŠƒA‚ª‰Ÿ‚³‚ê‚½ê‡
    public void clearClick(){
        calc = "";
        str = "";
        tv.setText("0");
        tv2.setText("");
    }

    //delete‚ª‰Ÿ‚³‚ê‚½ê‡(ÅŒã‚Ìˆê•¶š‚ğÁ‚·)
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

    //”’l‚ª‰Ÿ‚³‚ê‚½ê‡
    public void btnClick(View v){
        Button btnStr=(Button) v;
        str = btnStr.getText().toString();
        //Å‰‚É0‚à‚µ‚­‚Í‰‰ZqŒã‚É0‚ğ“ü‚ê‚½ê‡return
        if(str.equals("0") && (calc.equals("") || calc.endsWith("+") || calc.endsWith("-") || calc.endsWith("*") || calc.endsWith("/") )){
            return;
        }

        //Å‰‚É‰‰Zq‚ğ“ü‚ê‚³‚¹‚È‚¢
        if(calc.equals("") && ( str.endsWith("+") || str.endsWith("-") || str.endsWith("*") || str.endsWith("/") )){
            return;
        }

        //‰‰Zq‚Ì‚ ‚Æ‚É‰‰Zq‚ğ“ü‚ê‚³‚¹‚È‚¢
        if( (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") ) && ( calc.endsWith("+") || calc.endsWith("-") || calc.endsWith("*") || calc.endsWith("/") )){
            return;
        }
        tv2.setText("");
        calc += str;
        tv.setText(calc);
    }


    //equal‚ª‰Ÿ‚³‚ê‚½ê‡
    public void equalClick(){

        /*calc‚ÌÅŒã‚ª‰‰Zq‚Ìê‡return*/
        if(calc.endsWith("+") || calc.endsWith("-") || calc.endsWith("*") || calc.endsWith("/")){
            return;
        }

        //ArrayList‚ÉŠi”[‚·‚é
        ArrayList<String> caluList = new ArrayList<String>();//ŒvZ®Ši”[—pList
	    String calc_part ="" ;
	    int j = 0;
	    
	    for(int i = 0 ; i < calc.length(); i++){
	    	calc_part = String.valueOf(calc.charAt(i));
	    	
	    	//calc_part‚ª‰‰Zq‚¾‚Á‚½‚ç‚»‚ê‚Ü‚Å‚Ì”’l‚ğ‚Ğ‚Æ‚Ü‚Æ‚ß‚Æ‚µ‚Älist‚ÉŠi”[‚·‚é
	           if(calc_part.equals("+") || calc_part.equals("-") || calc_part.equals("/") || calc_part.equals("*")){
	        	   String num = "";
	        	   //System.out.println("‰‰Zq‚ªŒ©‚Â‚©‚Á‚½‚æ");
	        	   //String operator = "";
	        	   //”’l‚ğŠi”[
	        	   //System.out.println(String.valueOf(calc.charAt(0)));
	        	   //System.out.println(j);
	        	   for(; j<i; j++){
	        		   //System.out.println(String.valueOf(calc.charAt(j)));
	        		   num += String.valueOf(calc.charAt(j));
	        		   //System.out.println(num);
	        	   }
	        	   caluList.add(num);
	        	   //‰‰Zq‚ğŠi”[
	        	   caluList.add(String.valueOf(calc.charAt(i)));
	        	   
	        	   //Ÿ‚ÌŠJnˆÊ’u‚ğŒ©‚Â‚©‚Á‚½‰‰Zq‚ÌŸ‚É‚·‚é
	        	   j = i+1;
	           }
	           
	           //ÅŒã‚Ü‚Å‰‰Zq‚ªŒ©‚Â‚©‚ç‚È‚©‚Á‚½‚ç
	           if(i == calc.length()-1){
	        	   String num = "";
	        	   for(; j < calc.length(); j++){
	        		   num += String.valueOf(calc.charAt(j));
	        	   }
	        	   caluList.add(num);
	           }
	    }
	    
	    Deque<String> operStack = new ArrayDeque<String>(); //”’l‹y‚Ñ‰‰Zq—pƒXƒ^ƒbƒN
		Deque<String> caluStack = new ArrayDeque<String>(); //ˆê“I‚È‰‰ZqƒXƒ^ƒbƒN
		Deque<String> polishStack = new ArrayDeque<String>(); //‹tƒ|[ƒ‰ƒ“ƒh•û®‚É‚µ‚½ŒvZ®—pƒXƒ^ƒbƒN
		Deque<Double> ansStack = new ArrayDeque<Double>(); //‰‰ZŒ‹‰Ê—pƒXƒ^ƒbƒN
		
		for(int i = 0; i<caluList.size(); i++){
			//”’l‚¾‚Á‚½‚çcaluStack‚ÉŠi”[
			if(!caluList.get(i).equals("+") && !caluList.get(i).equals("-") && !caluList.get(i).equals("*") && !caluList.get(i).equals("/")){ 
				//System.out.println(caluList.get(i));
				caluStack.push(caluList.get(i));
				
			//‰‰Zq‚Ìê‡
			}else {
				//operStack‚É‰‰Zq‚ªŠi”[‚³‚ê‚Ä‚¢‚È‚©‚Á‚½‚ç‚»‚Ì‚Ü‚ÜoperStack‚ÉŠi”[
				if(operStack.peek()==null){
					operStack.push(caluList.get(i));
				
				//operStack‚É‰‰Zq‚ªŠi”[‚³‚ê‚Ä‚¢‚½ê‡@“Ç‚İ‚Ü‚ê‚½‰‰Zq‚Ì—Dæ“x‚ª‚‚¯‚ê‚Î‚»‚Ì‚Ü‚ÜoperStack‚ÉŠi”[@
				//‚»‚êˆÈŠO‚ÍoperStack‚ÉŠi”[‚³‚ê‚Ä‚é‚à‚Ì‚ğcaluStack‚ÉŠi”[‚µ“Ç‚İ‚ñ‚¾‰‰Zq‚ğoperStack‚ÉŠi”[
				}else {
					//ŠÖ”‚ğŒÄ‚Ño‚µƒXƒ^ƒbƒN‚Ì‰‰Zq‚Ì—Dæ“x‚Æ“Ç‚İ‚ñ‚¾‰‰Zq‚Ì—Dæ“x‚ğŒˆ’è‚·‚é
					OperPriority Op = new OperPriority();
					int CaluOp01 = Op.OperPriority01(caluList.get(i));
					int CaluOp02 = Op.OperPriority02(operStack.peek());
					
					//“Ç‚İ‚ñ‚¾‰‰Zq‚Ì—Dæ“x‚Ì•û‚ª‚‚©‚Á‚½‚Ì‚Å‚»‚Ì‚Ü‚ÜoperStack‚ÉŠi”[
					if(CaluOp01>CaluOp02){
						operStack.push(caluList.get(i));
					}else { //operStack‚ÉŠi”[‚³‚ê‚Ä‚é‚à‚Ì‚ğcaluStack‚ÉŠi”[‚µA“Ç‚İ‚ñ‚¾‰‰Zq‚ğoperStack‚ÉŠi”[
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
		
		//‹tƒ|[ƒ‰ƒ“ƒh•û®‚©‚çŒ‹‰Ê‚ğ‹‚ß‚é
				Double num1 = (double) 0;//ŒvZ®—p‚Ì•Ï”
				Double num2 = (double) 0;//ŒvZ®—p‚Ì•Ï”
				String opreDelete = null;//g—p‚µ‚½‰‰Zq‚ğŠi”[‚·‚é•Ï”
				
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
				//Å‰‚É‚ğ‰Ÿ‚·‚ÆƒGƒ‰[
				
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
