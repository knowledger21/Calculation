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
    String calc ;//�v�Z���i�[�p
    String str; //btn�������ꂽ�ꍇ�i�[�p
	
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
    
  //�N���A�������ꂽ�ꍇ
    public void clearClick(){
        calc = "";
        str = "";
        tv.setText("0");
        tv2.setText("");
    }

    //delete�������ꂽ�ꍇ(�Ō�̈ꕶ��������)
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

    //���l�������ꂽ�ꍇ
    public void btnClick(View v){
        Button btnStr=(Button) v;
        str = btnStr.getText().toString();
        //�ŏ���0�������͉��Z�q���0����ꂽ�ꍇreturn
        if(str.equals("0") && (calc.equals("") || calc.endsWith("+") || calc.endsWith("-") || calc.endsWith("*") || calc.endsWith("/") )){
            return;
        }

        //�ŏ��ɉ��Z�q����ꂳ���Ȃ�
        if(calc.equals("") && ( str.endsWith("+") || str.endsWith("-") || str.endsWith("*") || str.endsWith("/") )){
            return;
        }

        //���Z�q�̂��Ƃɉ��Z�q����ꂳ���Ȃ�
        if( (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") ) && ( calc.endsWith("+") || calc.endsWith("-") || calc.endsWith("*") || calc.endsWith("/") )){
            return;
        }
        tv2.setText("");
        calc += str;
        tv.setText(calc);
    }


    //equal�������ꂽ�ꍇ
    public void equalClick(){

        /*calc�̍Ōオ���Z�q�̏ꍇreturn*/
        if(calc.endsWith("+") || calc.endsWith("-") || calc.endsWith("*") || calc.endsWith("/")){
            return;
        }

        //ArrayList�Ɋi�[����
        ArrayList<String> caluList = new ArrayList<String>();//�v�Z���i�[�pList
	    String calc_part ="" ;
	    int j = 0;
	    
	    for(int i = 0 ; i < calc.length(); i++){
	    	calc_part = String.valueOf(calc.charAt(i));
	    	
	    	//calc_part�����Z�q�������炻��܂ł̐��l���ЂƂ܂Ƃ߂Ƃ���list�Ɋi�[����
	           if(calc_part.equals("+") || calc_part.equals("-") || calc_part.equals("/") || calc_part.equals("*")){
	        	   String num = "";
	        	   //System.out.println("���Z�q������������");
	        	   //String operator = "";
	        	   //���l���i�[
	        	   //System.out.println(String.valueOf(calc.charAt(0)));
	        	   //System.out.println(j);
	        	   for(; j<i; j++){
	        		   //System.out.println(String.valueOf(calc.charAt(j)));
	        		   num += String.valueOf(calc.charAt(j));
	        		   //System.out.println(num);
	        	   }
	        	   caluList.add(num);
	        	   //���Z�q���i�[
	        	   caluList.add(String.valueOf(calc.charAt(i)));
	        	   
	        	   //���̊J�n�ʒu�������������Z�q�̎��ɂ���
	        	   j = i+1;
	           }
	           
	           //�Ō�܂ŉ��Z�q��������Ȃ�������
	           if(i == calc.length()-1){
	        	   String num = "";
	        	   for(; j < calc.length(); j++){
	        		   num += String.valueOf(calc.charAt(j));
	        	   }
	        	   caluList.add(num);
	           }
	    }
	    
	    Deque<String> operStack = new ArrayDeque<String>(); //���l�y�щ��Z�q�p�X�^�b�N
		Deque<String> caluStack = new ArrayDeque<String>(); //�ꎞ�I�ȉ��Z�q�X�^�b�N
		Deque<String> polishStack = new ArrayDeque<String>(); //�t�|�[�����h�����ɂ����v�Z���p�X�^�b�N
		Deque<Double> ansStack = new ArrayDeque<Double>(); //���Z���ʗp�X�^�b�N
		
		for(int i = 0; i<caluList.size(); i++){
			//���l��������caluStack�Ɋi�[
			if(!caluList.get(i).equals("+") && !caluList.get(i).equals("-") && !caluList.get(i).equals("*") && !caluList.get(i).equals("/")){ 
				//System.out.println(caluList.get(i));
				caluStack.push(caluList.get(i));
				
			//���Z�q�̏ꍇ
			}else {
				//operStack�ɉ��Z�q���i�[����Ă��Ȃ������炻�̂܂�operStack�Ɋi�[
				if(operStack.peek()==null){
					operStack.push(caluList.get(i));
				
				//operStack�ɉ��Z�q���i�[����Ă����ꍇ�@�ǂݍ��܂ꂽ���Z�q�̗D��x��������΂��̂܂�operStack�Ɋi�[�@
				//����ȊO��operStack�Ɋi�[����Ă���̂�caluStack�Ɋi�[���ǂݍ��񂾉��Z�q��operStack�Ɋi�[
				}else {
					//�֐����Ăяo���X�^�b�N�̉��Z�q�̗D��x�Ɠǂݍ��񂾉��Z�q�̗D��x�����肷��
					OperPriority Op = new OperPriority();
					int CaluOp01 = Op.OperPriority01(caluList.get(i));
					int CaluOp02 = Op.OperPriority02(operStack.peek());
					
					//�ǂݍ��񂾉��Z�q�̗D��x�̕������������̂ł��̂܂�operStack�Ɋi�[
					if(CaluOp01>CaluOp02){
						operStack.push(caluList.get(i));
					}else { //operStack�Ɋi�[����Ă���̂�caluStack�Ɋi�[���A�ǂݍ��񂾉��Z�q��operStack�Ɋi�[
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
		
		//�t�|�[�����h�������猋�ʂ����߂�
				Double num1 = (double) 0;//�v�Z���p�̕ϐ�
				Double num2 = (double) 0;//�v�Z���p�̕ϐ�
				String opreDelete = null;//�g�p�������Z�q���i�[����ϐ�
				
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
				//�ŏ��Ɂ��������ƃG���[
				
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
