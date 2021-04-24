package tp2;

import java.util.Scanner;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
public class Affectation_PM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		System.out.print(" nombre de personne et de machine :");
		int n=sc.nextInt();

		int [][]pr=new int[n][n];
	
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++) {
				System.out.println("la productivité entre la personne " +i+" et machine  " +j +":" );

				pr[i][j]=sc.nextInt();

			}
		}
		calcul (n,pr);

	}
	public static void calcul(int n , int pr [][]) {



		try {
			// define new model
			IloCplex opl = new IloCplex();
			IloNumVar[][] x = new IloNumVar[n][n];
			for(int i=0;i<n;i++) {
				for(int j =0;j<n;j++) {
					x[i][j]= opl.numVar(0,1);
				}
			}

			IloLinearNumExpr objective = opl.linearNumExpr();
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					objective.addTerm(pr[i][j],x[i][j]);
				}
			}
			opl.addMaximize(objective);
			// contraintes


			for (int i=0; i<n; i++) {
				opl.addLe(opl.sum(x[i]),1);

			}
			IloNumVar[][] tr = new IloNumVar[n][n];
			
			  for(int i = 0; i<n ;i++){
			         for(int j = 0; j<n ;j++){
			        	 tr[i][j] = x[j][i];
			            
			         }
			       
			      }

			  for (int i=0; i<n; i++) {
					opl.addLe(opl.sum(tr[i]),1);

				}






			opl.solve();
			System.out.println("Voici la valeur de la fonction objectif : "+opl.getObjValue() +"\n");

			System.out.println("Voici les valeurs des variables de décision:  ");
			for (int i=0;i<n;i++) {
				System.out.println("---------------------------------------------- " );
				for (int j=0; j<n; j++) {

					System.out.println( "X"+i+ ""+j+"= "+ opl.getValue(x[i][j]));}
			}

			opl.end();

		}catch(IloException exc){
			System.out.print("Exception levée " + exc);;
		}
	}

}
