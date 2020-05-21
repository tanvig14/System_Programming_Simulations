import java.io.*;

class MOT 
{
	String mnemonic;
	int opcode;
	int operands;
	int length;
}

class POT
{
	String pseudo_opcode;
	int operands;
	int length;
}

class Sym_Table
{
	String symbol;
	int address;
}

public class Assembler
{
	public static MOT mot[] = new MOT[12];
	public static POT pot[] = new POT[6];
	public static Sym_Table sym_tbl[] = new Sym_Table[20];
	
	public static FileReader inputReader = null;
	public static BufferedReader inputBuffer;
	public static FileWriter outputWriter = null;
	
	public static String str = null;
	public static int symbol_count = 0;
	public static int LC = 0;
	
	public static void init()
	{
		for(int i = 0; i < 12; i++)
			mot[i] = new MOT();
    
		mot[0].mnemonic = "ADD"; mot[0].opcode = 1; mot[0].operands = 1; mot[0].length = 2;
		mot[1].mnemonic = "SUB"; mot[1].opcode = 2; mot[1].operands = 1; mot[1].length = 2;
		mot[2].mnemonic = "MULT"; mot[2].opcode = 3; mot[2].operands = 1; mot[2].length = 2;
		mot[3].mnemonic = "JMP"; mot[3].opcode = 4; mot[3].operands = 1; mot[3].length = 2;
		mot[4].mnemonic = "JNEG"; mot[4].opcode = 5; mot[4].operands = 1; mot[4].length = 2;
		mot[5].mnemonic = "JPOS"; mot[5].opcode = 6; mot[5].operands = 1; mot[5].length = 2;
		mot[6].mnemonic = "JZ"; mot[6].opcode = 7; mot[6].operands = 1; mot[6].length = 2;
		mot[7].mnemonic = "Load"; mot[7].opcode = 8; mot[7].operands = 1; mot[7].length = 2;
		mot[8].mnemonic = "Store"; mot[8].opcode = 9; mot[8].operands = 1; mot[8].length = 2;
		mot[9].mnemonic = "Read"; mot[9].opcode = 10; mot[9].operands = 1; mot[9].length = 2;
		mot[10].mnemonic = "Write"; mot[10].opcode = 11; mot[10].operands = 1; mot[10].length = 2;
		mot[11].mnemonic = "Stop"; mot[11].opcode = 12; mot[11].operands = 0; mot[11].length = 1;
		
		System.out.println("\n\t\t\t MOT \n");
		System.out.println(" Mnemonic \t Opcode \t Operands \t Length");
		for(int i = 0; i < 12; i++)
			System.out.println("  " + mot[i].mnemonic + "\t\t   " + mot[i].opcode + "\t\t   " + mot[i].operands + "\t\t   " + mot[i].length);
		
		for(int i = 0; i < 6; i++)
			pot[i] = new POT();
    
		pot[0].pseudo_opcode = "DB"; pot[0].operands = 2; pot[0].length = 1;
		pot[1].pseudo_opcode = "DW"; pot[1].operands = 2; pot[1].length = 1;
		pot[2].pseudo_opcode = "ORG"; pot[2].operands = 1; pot[2].length = 1;
		pot[3].pseudo_opcode = "ENDP"; pot[3].operands = 1; pot[3].length = 1;
		pot[4].pseudo_opcode = "CONST"; pot[4].operands = 1; pot[4].length = 1;
		pot[5].pseudo_opcode = "END"; pot[5].operands = 0; pot[5].length = 1;
		
		System.out.println("\n\n\t\t POT \n");
		System.out.println(" Pseudo \t Operands \t Length");
		for(int i = 0; i < 6; i++)
    {
			System.out.println("  " + pot[i].pseudo_opcode + "\t\t   " + pot[i].operands + "\t\t   " + pot[i].length);
		}
		System.out.println("\n");
	} 
	
	public static void Display_Symbol_Table()
	{
		System.out.println("\n\t Symbol Table \n");
		System.out.println(" Symbol \t Address ");
		for(int i = 0; i < symbol_count; i++)
			System.out.println("  " + sym_tbl[i].symbol + "\t\t   " + sym_tbl[i].address);
    System.out.println("\n");
	} 
	
	public static void Pass1() throws IOException, FileNotFoundException
	{
		System.out.println("\n Pass 1 ");
		
		inputReader = new FileReader("input_file.txt");
		inputBuffer = new BufferedReader(inputReader);
		
		str = inputBuffer.readLine();
		while(!str.equals("Endp"))										
		{
			int temp_index, i;
			String temp[] = str.split(" ");
			if(temp.length == 1)										
			{
				LC++;
			}
			else
			{
				if(temp.length == 2)
					temp_index = 0;
				else													
				{
					sym_tbl[symbol_count] = new Sym_Table();			
					String temp_label[] = temp[0].split(":");
					
					sym_tbl[symbol_count].symbol = temp_label[0];
					sym_tbl[symbol_count].address = LC;
					symbol_count++;
					
					temp_index = 1;
				}
				
				for(i = 0; i < 12; i++)
				{
					if(temp[temp_index].equals(mot[i].mnemonic))
					{
						LC += mot[i].length;
					} 
				} 
				
				for(i = 0; i < symbol_count; i++)
				{
					if(sym_tbl[i].symbol.equals(temp[temp_index+1]))
						break;
				}
				
				if(i == symbol_count)
				{
					//System.out.println(" Symbol " + temp[temp_index+1] + " not present ");
					//System.out.println(" Command - " + temp[temp_index]);
					if((temp[temp_index].equals("JMP")) || (temp[temp_index].equals("JNEG")) || (temp[temp_index].equals("JPOS")) || (temp[temp_index].equals("JZ")))
					{
						str = inputBuffer.readLine();
						continue;
					}
					else
					{
						sym_tbl[symbol_count] = new Sym_Table();
						sym_tbl[symbol_count].symbol = temp[temp_index+1];
						symbol_count++;
					}
				} 
			}
			str = inputBuffer.readLine();
		} 
		
		System.out.println("\n\n User defined variables \n");
		str = inputBuffer.readLine();
		while(!str.equals("END"))										                                        //UDV
		{
			System.out.println(str);
			
			String temp[] = str.split(" ");
			int sym_tbl_index = 0;
			while(sym_tbl_index < symbol_count)
			{
				if(sym_tbl[sym_tbl_index].symbol.equals(temp[0]))
					break;
				sym_tbl_index++;
			}
			System.out.println(" Sym Table index = " + sym_tbl_index);
			if(sym_tbl_index < symbol_count)
			{
				sym_tbl[sym_tbl_index].address = LC;
				LC++;
			}
			
			str = inputBuffer.readLine();
		} 
		System.out.println("\n");
		
		inputReader.close();
		inputBuffer.close();
		
		Display_Symbol_Table();
	} 
	
	public static void Pass2() throws IOException, FileNotFoundException
	{
		System.out.println("\n Pass 2 \n");
		
		inputReader = new FileReader("input_file.txt");
		inputBuffer = new BufferedReader(inputReader);
		
		outputWriter = new FileWriter("target_program.txt");
		
		str = inputBuffer.readLine();
		while(!str.equals("Endp"))
		{
			System.out.println(str);
			
			if(str.equals("Stop"))
			{
				outputWriter.append("12" + "\n");
			}
			else
			{
				String temp[] = str.split(" ");
				int opcode, address;
				int temp_index = 0, i;
				
				if(temp.length == 3)
					temp_index = 1;
				
				i = 0;
				while(i < 12)												                                            //Finding opcode of command
				{
					if(mot[i].mnemonic.equals(temp[temp_index]))
						break;
					i++;
				}
				opcode = mot[i].opcode;
				
				i = 0;
				while(i < symbol_count)										                                      //Finding address of variable
				{
					if(sym_tbl[i].symbol.equals(temp[temp_index+1]))
						break;
					i++;
				}
				address = sym_tbl[i].address;
				
				System.out.println(opcode + "\t" + address + "\n");
				outputWriter.append(opcode + "\t" + address + "\n");
			}
			
			str = inputBuffer.readLine();
		}
		System.out.println("\n");
		
		inputReader.close();
		inputBuffer.close();
		outputWriter.close();
	} //Pass2()
	
	public static void main(String[] args) throws IOException, FileNotFoundException
	{
		init();
		
		try
		{
			Pass1();	
			Pass2();
			
		} 
		catch(Exception e)
		{
			System.out.print(" Exception handled : ");
			e.printStackTrace();
		} 
		
		finally
		{
			inputReader.close();
			inputBuffer.close();
			outputWriter.close();
		}
	}
}
