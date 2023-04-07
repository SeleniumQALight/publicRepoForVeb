public class Matiashchuk {
	private static String name;
	private static int age;

	public Matiashchuk(String name, int age) {
		Matiashchuk.name = name;
		Matiashchuk.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setName(String name) {
		Matiashchuk.name = name;
	}

	public void setAge(int age) {
		Matiashchuk.age = age;
	}
}
