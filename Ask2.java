import java.util.Scanner;

class DefineConstants
{
    public static final int MAX = 1;
    public static final int MIN = -1;
    public static final int TIE = 0;
    public static final int GAME_END = 100;
    public static final int GAME_CONTINUE = 200;
}

class min_max
{   public min_max[] pointers = new min_max[16];
	public int next;
    public int free_boxes;
    public char[][] table = new char[3][3];
}


public class Ask2 {

    public static int check_SOS(char[][] table)
    {
        if (table[0][0]=='S' && table[0][1]=='O' && table[0][2]=='S') // grammi: 1
        {
            return (DefineConstants.GAME_END);
        }
        else if (table[2][0]=='S' && table[2][1]=='O' && table[2][2]=='S') // grammi: 3
        {
            return (DefineConstants.GAME_END);
        }
        else if (table[0][0]=='S' && table[1][0]=='O' && table[2][0]=='S') // stili: 1
        {
            return (DefineConstants.GAME_END);
        }
        else if (table[0][1]=='S' && table[1][1]=='O' && table[2][1]=='S') // stili: 2
        {
            return (DefineConstants.GAME_END);
        }
        else if (table[0][2]=='S' && table[1][2]=='O' && table[2][2]=='S') // stili: 3
        {
            return (DefineConstants.GAME_END);
        }
        else if (table[0][0]=='S' && table[1][1]=='O' && table[2][2]=='S') // kiria diagonios
        {
            return (DefineConstants.GAME_END);
        }
        else if (table[2][0]=='S' && table[1][1]=='O' && table[0][2]=='S') // alli diagonios
        {
            return (DefineConstants.GAME_END);
        }
        else
        {
            return (DefineConstants.GAME_CONTINUE);
        }

    }

    public static void copy_table(char[][] A, char[][] B)
    {
        int i;
        int j;
        for (i = 0;i < 3;i++)
        {
            for (j = 0;j < 3;j++)
            {
                B[i][j] = A[i][j];
            }
        }

    }

	public static int make_children(min_max tree, int seira, int epomenos, int[] test)
    {
        int position = 0;
        int i;
        int j;

        for (i = 0;i < 3;i++)
        {
            for (j = 0;j < 3;j++)
            {
                if (tree.table[i][j]=='-')
                {
                    tree.pointers[position] = new min_max();
                    tree.pointers[position].free_boxes = tree.free_boxes - 1;
                    copy_table(tree.table, tree.pointers[position].table);
                    tree.pointers[position].table[i][j] = 'S';
                    test[position] = dentro_MINMAX(tree.pointers[position], epomenos);
                    position++;

                    tree.pointers[position] = new min_max();
                    tree.pointers[position].free_boxes = tree.free_boxes - 1;
                    copy_table(tree.table, tree.pointers[position].table);
                    tree.pointers[position].table[i][j] = 'O';
                    test[position] = dentro_MINMAX(tree.pointers[position], epomenos);
                    position++;
                }
            }
        }

        return position;
    }

    public static int dentro_MINMAX(min_max tree, int seira)
    {
        int epomenos;
        int position = 0;
        int[] test = new int[16];
        int kaliteri_timi;
		int best;
        int i;

        if (seira == DefineConstants.MAX && check_SOS(tree.table) == DefineConstants.GAME_END)
        {	
            return (DefineConstants.MIN);
        }
        else if (seira == DefineConstants.MIN && check_SOS(tree.table) == DefineConstants.GAME_END)
        {	
            return (DefineConstants.MAX);
        }
        else if (tree.free_boxes == 0)
        {	
            return (DefineConstants.TIE);
        }

        if (seira == DefineConstants.MAX)
        {
            epomenos = DefineConstants.MIN;
        }
        else
        {
            epomenos = DefineConstants.MAX;
        }

        position = make_children(tree, seira, epomenos, test);

        if (seira == DefineConstants.MAX)
        {
            kaliteri_timi = test[0];
			best=0;
            for (i = 0;i < position;i++)
            {
                if (test[i] > kaliteri_timi)
                {
                    kaliteri_timi = test[i];
					best=i;
                }
            }
        }
        else
        {
            kaliteri_timi = test[0];
			best=0;
            for (i = 1;i < position;i++)
            {
                if (test[i] < kaliteri_timi)
                {
                    kaliteri_timi = test[i];
					best=i;
                }
            }
        }
		tree.next =  best;
        return kaliteri_timi;
    }

    public static void print_table(char[][] A)
    {
        int i;
        int j;
        for (i = 0;i < 3;i++)
        {
            for (j = 0;j < 3;j++)
            {
                System.out.printf("%s ",A[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n\n\n");
    }

    public static void main(String[] args) {

        min_max state = new min_max();
        int seira = DefineConstants.MAX;
        int i;
        int j;
        int grammi;
        int stili;
        char haraktiras;
        Scanner scan = new Scanner(System.in);

        for (i = 0;i < 3;i++)
        {
            for (j = 0;j < 3;j++)
            {  state.table[i][j]='-';
            }
        }
        state.free_boxes = 8;

        state.table[1][0]='O';  // allios: state.table[1][2]='O';

        System.out.print("ARXI PAIXNIDIOU \n");

        print_table(state.table);

        while(true)
        {  if(seira==DefineConstants.MAX && check_SOS(state.table)== DefineConstants.GAME_END)
           {  System.out.print("NIKHSE O ANTIPALOS \n\n\n");
              System.out.println("Press Any Key To Continue...");
              new java.util.Scanner(System.in).nextLine();
              break;
           }
           else if(seira==DefineConstants.MIN && check_SOS(state.table)==DefineConstants.GAME_END)
           {  System.out.print("NIKHSE TO PROGRAMMA  \n\n\n");
              System.out.println("Press Any Key To Continue...");
              new java.util.Scanner(System.in).nextLine();
              break;
           }
           else if(state.free_boxes==0)
           {  System.out.print("ISOPALIA  \n\n\n");
              System.out.println("Press Any Key To Continue...");
              new java.util.Scanner(System.in).nextLine();
              break;
           }


            if(seira==DefineConstants.MAX)
            {  System.out.print("PROGRAMMA paizei  \n\n\n");
               dentro_MINMAX(state,DefineConstants.MAX);
               state=state.pointers[state.next];
               print_table(state.table);
               seira=DefineConstants.MIN;
            }
            else
            {   System.out.print("ANTIPALOS paizei  \n\n\n");

                System.out.print("Epilekste haraktira: S / O ? \n");
                haraktiras=scan.next().charAt(0);

                System.out.print("Epilekste grammi: 1 / 2 / 3 ? \n");
                grammi = scan.nextInt();
                scan.nextLine();

                System.out.print("Epilekste stili: 1 / 2 / 3 ? ");
                stili = scan.nextInt();
                scan.nextLine();

                System.out.print("\n");

                state.table[grammi-1][stili-1]= haraktiras;
                state.free_boxes= state.free_boxes-1;

                print_table(state.table);
                seira=DefineConstants.MAX;
            }
        }
    }
}
