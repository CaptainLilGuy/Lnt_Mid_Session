import java.util.Scanner;

public class Main {
	
	Scanner scan = new Scanner(System.in);
	
	Menus menu = new Menus();
	
	public Main() {
		while(true) {
			System.out.println("Chipi Chapa");
			System.out.println("=================");
			System.out.println("1. Insert data karyawan");
			System.out.println("2. View data karyawan");
			System.out.println("3. Update data karyawan");
			System.out.println("4. Delete data karyawan");
			System.out.println("5. Exit");
			int input;
			selectMenu:
				while(true) {
					try {
						System.out.print(">> ");
						int choice = scan.nextInt(); scan.nextLine();
						switch (choice) {
						case 1:
							input = 1;
							break selectMenu;
						case 2:
							input = 2;
							break selectMenu;
						case 3:
							input = 3;
							break selectMenu;
						case 4:
							input = 4;
							break selectMenu;
						case 5:
							input = 5;
							break selectMenu;
						default:
							System.out.println("Command tidak dikenal");
						}
					} catch (Exception InputMismatchException) {
						System.out.println("Input harus integer");
						scan.nextLine();
					}
				}
			
			switch(input) {
			case 1:
				menu.insert();
				break;
			case 2:
				menu.view();
				break;
			case 3:
				menu.update();
				break;
			case 4:
				menu.delete();
				break;
			case 5:
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		new Main();

	}

}
