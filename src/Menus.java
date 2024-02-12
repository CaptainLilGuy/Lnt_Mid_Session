import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Menus {

	ArrayList<ObjKaryawan> listKaryawan = new ArrayList<>();
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	
	public void insert() {
		String kodeKaryawan, namaKaryawan, jenisKelamin, jabatan;
		int gaji = 0;
		
		kodeKaryawan = generateCode();
		
		while(true) {
			System.out.print("Input nama karyawan [>=3]: ");
			namaKaryawan = scan.nextLine();
			if(namaKaryawan.length()>=3)
				break;
			else
				System.out.println("Nama harus lebih dari 3 huruf");
		}
		
		while(true) {
			System.out.print("Input jenis Kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
			jenisKelamin = scan.nextLine();
			if((jenisKelamin.equals("Laki-laki"))||(jenisKelamin.equals("Perempuan")))
				break;
			else
				System.out.println("Jenis kelamin harus 'Laki-laki' atau 'Perempuan' ");
		}
		
		while(true) {
			System.out.print("Input jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
			jabatan = scan.nextLine();
			if( (jabatan.equals("Manager")) || (jabatan.equals("Supervisor")) || (jabatan.equals("Admin")) )
				break;
			else
				System.out.println("Jabatan harus 'Manager', 'Supervisor', atau 'Admin' ");
		}
		
		switch(jabatan) {
		case "Manager":
			gaji = 8000000;
			break;
		case "Supervisor":
			gaji = 6000000;
			break;
		case "Admin":
			gaji = 4000000;
			break;
		}
		
		listKaryawan.add(new ObjKaryawan(namaKaryawan, kodeKaryawan, jenisKelamin, jabatan, gaji));
		System.out.println("Berhasil menambahkan karyawan dengan id: " + kodeKaryawan);
		hitungNaikGaji(jabatan);
		System.out.println("ENTER to return");
		scan.nextLine();
		
	}
	
	public void view() {
		if(listKaryawan.isEmpty()) {
			System.out.println("Data karyawan kosong");
			System.out.println("ENTER to return");
			scan.nextLine();
		} else {
			sort();
			viewData();
			System.out.println("ENTER to return");
			scan.nextLine();
		}
	}
	
	public void update() {
		if(listKaryawan.isEmpty()) {
			System.out.println("Data karyawan kosong");
			System.out.println("ENTER to return");
			scan.nextLine();
		} else {
			viewData();
			int listSize = listKaryawan.size();
			int choice;
			while (true) {
				try {
					System.out.print("Input nomor yang ingin diupdate: ");
					int input = scan.nextInt(); scan.nextLine();
					if ((input <= (listSize)) && (input > 0)) {
						choice = input;
						break;
					}
				} catch (Exception InputMismatchException) {
					System.out.println("Input harus berupa integer");
					scan.nextLine();
				} 
			}
			updateingData(choice);
			System.out.println("ENTER to return");
			scan.nextLine();
		}
	}
	
	public void delete() {
		if(listKaryawan.isEmpty()) {
			System.out.println("Data karyawan kosong");
			System.out.println("ENTER to return");
			scan.nextLine();
		} else {
			viewData();
			int listSize = listKaryawan.size();
			while(true) {
				try {
					System.out.print("Input nomor yang ingin dihapus: ");
					int input = scan.nextInt(); scan.nextLine();
					if ((input <= (listSize)) && (input > 0)) {
						listKaryawan.remove(input-1);
						sort();
						System.out.println("Data berhasil dihapus");
						System.out.println("ENTER to return");
						scan.nextLine();
						break;
					}
				} catch (Exception InputMismatchException) {
					System.out.println("Input harus berupa integer");
					scan.nextLine();
				}
			}
		}
	}
	
	private String generateCode() {
		char firsAlfa = (char) (rand.nextInt(26)+65);
		char secAlfa = (char) (rand.nextInt(26)+65);
		String alfa = Character.toString(firsAlfa) + Character.toString(secAlfa);
		int a = rand.nextInt(10);
		int b = rand.nextInt(10);
		int c = rand.nextInt(10);
		int d = rand.nextInt(10);
		String kodeKaryawan =  alfa + "-" +  a + b + c + d;
		boolean sameKode;
		do {
			sameKode = false;
			for (int i = 0; i < listKaryawan.size(); i++) {
				if(listKaryawan.get(i).kodeKaryawan.equals(kodeKaryawan)) {
					sameKode = true;
				}
			}
			kodeKaryawan = alfa + "-" + a + b + c + d;
		} while (sameKode==true);
		return kodeKaryawan;
	}
	
	private void hitungNaikGaji(String jabatan) {
		
		switch (jabatan) {
		case "Manager":
			int counterManager = 0;
			ArrayList<String> listKodeManager = new ArrayList<>();
			
			for (int i = 0; i < listKaryawan.size(); i++) {
				if(listKaryawan.get(i).jabatan.contains("Manager")) {
					counterManager++;
					listKodeManager.add(listKaryawan.get(i).kodeKaryawan);
				}
			}
			int counterNaikM = 0;
			if(((counterManager-1)%3==0) && ((counterManager-1)!=0)) {
				for (int i = 0; i < listKodeManager.size()-1; i++) {
					for (int j = 0; j < listKaryawan.size(); j++) {
						if(listKaryawan.get(j).kodeKaryawan.equals(listKodeManager.get(i))) {
							int newGaji = listKaryawan.get(j).gaji + (listKaryawan.get(j).gaji*10/100);
							listKaryawan.set(j, new ObjKaryawan(
									listKaryawan.get(j).namaKaryawan, 
									listKaryawan.get(j).kodeKaryawan, 
									listKaryawan.get(j).jenisKelamin, 
									listKaryawan.get(j).jabatan, 
									newGaji));
						}
					}
					counterNaikM++;
				}
			} else {
				break;
			}
			System.out.println("Bonus sebesar 10% telah diberikan kepada karyawan dengan id berikut:");
			for (int i = 0; i < counterNaikM; i++) {
				System.out.println(listKodeManager.get(i));
			}
			break;
		
		case "Supervisor":
			int counterSupervisor = 0;
			ArrayList<String> listKodeSupervisor = new ArrayList<>();
			
			for (int i = 0; i < listKaryawan.size(); i++) {
				if(listKaryawan.get(i).jabatan.contains("Supervisor")) {
					counterSupervisor++;
					listKodeSupervisor.add(listKaryawan.get(i).kodeKaryawan);
				}
			}
			int counterNaikS = 0;
			if(((counterSupervisor-1)%3==0) && ((counterSupervisor-1)!=0)) {
				for (int i = 0; i < listKodeSupervisor.size()-1; i++) {
					for (int j = 0; j < listKaryawan.size(); j++) {
						if(listKaryawan.get(j).kodeKaryawan.equals(listKodeSupervisor.get(i))) {
							int newGaji = listKaryawan.get(j).gaji + (listKaryawan.get(j).gaji*75/1000);
							listKaryawan.set(j, new ObjKaryawan(
									listKaryawan.get(j).namaKaryawan, 
									listKaryawan.get(j).kodeKaryawan, 
									listKaryawan.get(j).jenisKelamin, 
									listKaryawan.get(j).jabatan, 
									newGaji));
						}
					}
					counterNaikS++;
				}
			} else {
				break;
			}
			System.out.println("Bonus sebesar 7.5% telah diberikan kepada karyawan dengan id berikut:");
			for (int i = 0; i < counterNaikS; i++) {
				System.out.println(listKodeSupervisor.get(i));
			}
			break;
			
		case "Admin":
			int counterAdmin = 0;
			ArrayList<String> listKodeAdmin = new ArrayList<>();
			
			for (int i = 0; i < listKaryawan.size(); i++) {
				if(listKaryawan.get(i).jabatan.contains("Admin")) {
					counterAdmin++;
					listKodeAdmin.add(listKaryawan.get(i).kodeKaryawan);
				}
			}
			int counterNaikA = 0;
			if(((counterAdmin-1)%3==0) && (counterAdmin-1)!=0) {
				for (int i = 0; i < listKodeAdmin.size()-1; i++) {
					for (int j = 0; j < listKaryawan.size(); j++) {
						if(listKaryawan.get(j).kodeKaryawan.equals(listKodeAdmin.get(i))) {
							int newGaji = listKaryawan.get(j).gaji + (listKaryawan.get(j).gaji*5/100);
							listKaryawan.set(j, new ObjKaryawan(
									listKaryawan.get(j).namaKaryawan, 
									listKaryawan.get(j).kodeKaryawan, 
									listKaryawan.get(j).jenisKelamin, 
									listKaryawan.get(j).jabatan, 
									newGaji));
						}
					}
					counterNaikA++;
				}
			} else {
				break;
			}
			System.out.println("Bonus sebesar 5% telah diberikan kepada karyawan dengan id berikut:");
			for (int i = 0; i < counterNaikA; i++) {
				System.out.println(listKodeAdmin.get(i));
			}
			break;
		}
	}
	
	private void sort() {
		int n = listKaryawan.size();
		for (int i = 0; i < n-1; i++) {
			int min = i;
			for (int j = i+1; j < n; j++) {
				if(listKaryawan.get(j).namaKaryawan.compareTo(listKaryawan.get(min).namaKaryawan)>0) {
					min = j;
				}
			}
			String tempNama = listKaryawan.get(min).namaKaryawan;
			String tempKode = listKaryawan.get(min).kodeKaryawan;
			String tempKelamin = listKaryawan.get(min).jenisKelamin;
			String tempJabatan = listKaryawan.get(min).jabatan;
			int tempGaji = listKaryawan.get(min).gaji;
			listKaryawan.remove(min);
			listKaryawan.add(new ObjKaryawan(tempNama, tempKode, tempKelamin, tempJabatan, tempGaji));
		}
	}
	
	private void viewData() {
		System.out.println("|---|-----------------|------------------------------|--------------|-------------|-------------|");
		System.out.println("|No |Kode Karyawan    |Nama Karyawan                 |Jenis Kelamin |Jabatan      |Gaji Karyawan|");
		System.out.println("|---|-----------------|------------------------------|--------------|-------------|-------------|");
		for (int i = 0; i < listKaryawan.size(); i++) {
			System.out.printf("|%-3s|%-17s|%-30s|%-14s|%-13s|%-13s|\n", (i+1), listKaryawan.get(i).kodeKaryawan, listKaryawan.get(i).namaKaryawan, listKaryawan.get(i).jenisKelamin, listKaryawan.get(i).jabatan, listKaryawan.get(i).gaji);
		}
		System.out.println("|---|-----------------|------------------------------|--------------|-------------|-------------|");
	}
	
	private void updateingData(int a) {
		String namaKaryawanNew = "", jenisKelaminNew = "", jabatanNew = "";
		int gajiNew = 0;
		
		while(true) {
			System.out.print("Input nama karyawan [>=3]: ");
			if(scan.hasNextInt()) {
				int command = scan.nextInt(); scan.nextLine();
				if(command==0) {
					System.out.println("Skip");
					break;
				} else {
					System.out.println("Command tidak diketahui");
				}
			}else {
			namaKaryawanNew = scan.nextLine();
			if(namaKaryawanNew.length()>=3) {
				break;
			}
			else
				System.out.println("Nama harus lebih dari 3 huruf");
			}
		}
		
		while(true) {
			System.out.print("Input jenis Kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
			if(scan.hasNextInt()) {
				int command = scan.nextInt(); scan.nextLine();
				if(command==0) {
					System.out.println("Skip");
					break;
				} else {
					System.out.println("Command tidak diketahui");
				}
			} else {
				jenisKelaminNew = scan.nextLine();
				if((jenisKelaminNew.equals("Laki-laki"))||(jenisKelaminNew.equals("Perempuan")))
					break;
				else
					System.out.println("Jenis kelamin harus 'Laki-laki' atau 'Perempuan' ");
			}
		}
		
		while(true) {
			System.out.print("Input jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
			if(scan.hasNextInt()) {
				int command = scan.nextInt(); scan.nextLine();
				if(command==0) {
					System.out.println("Skip");
					break;
				} else {
					System.out.println("Command tidak diketahui");
				}
			} else {
				jabatanNew = scan.nextLine();
				if( (jabatanNew.equals("Manager")) || (jabatanNew.equals("Supervisor")) || (jabatanNew.equals("Admin")) ) {
					switch(jabatanNew) {
					case "Manager":
						gajiNew = 8000000;
						break;
					case "Supervisor":
						gajiNew = 6000000;
						break;
					case "Admin":
						gajiNew = 4000000;
						break;
					}
					break;
				}
				else {
					System.out.println("Jabatan harus 'Manager', 'Supervisor', atau 'Admin' ");
				}
			}
		}
		
		commitUpdate(namaKaryawanNew, jenisKelaminNew, jabatanNew, gajiNew, (a-1));
		System.out.println("Berhasil mengupdate karyawan dengan id " + listKaryawan.get(a-1).kodeKaryawan);
		
	}
	
	private void commitUpdate(String newNama, String newKelamin, String newJabatan, int newGaji, int indexOnList) {
		String nama = "", kelamin = "", jabatan = "", kode = listKaryawan.get(indexOnList).kodeKaryawan;
		int gaji = 0;
		
		if(newNama.isBlank()) {
			nama = listKaryawan.get(indexOnList).namaKaryawan;
		} else {
			nama = newNama;
		}
		
		if(newKelamin.isBlank()) {
			kelamin = listKaryawan.get(indexOnList).jenisKelamin;
		} else {
			kelamin = newKelamin;
		}
		
		if(newJabatan.isBlank()) {
			jabatan = listKaryawan.get(indexOnList).jabatan;
		} else {
			jabatan = newJabatan;
		}
		
		if(newGaji==0) {
			gaji = listKaryawan.get(indexOnList).gaji;
		} else {
			gaji = newGaji;
		}
		
		listKaryawan.set(indexOnList, new ObjKaryawan(nama, kode, kelamin, jabatan, gaji));
		sort();
		hitungNaikGaji(jabatan);
	}
}
