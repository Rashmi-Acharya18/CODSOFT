import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Contact implements Serializable {
    String name;
    String phoneNumber;
    String emailAddr;

    Contact(String name, String phno, String email) {
        this.name = name;
        this.phoneNumber = phno;
        this.emailAddr = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return emailAddr;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + emailAddr;
    }
}

class AddressBook {

    ArrayList<Contact> contactList;

    // Constructor to create a new address book object
    AddressBook() {
        contactList = new ArrayList<>();
    }

    // Method to add contacts in the address book
    void addContact(String nm, String phno, String id) {
        if (isValidEmail(id)) {
            System.out.println("Invalid Email ID");
        } else {
            contactList.add(new Contact(nm, phno, id));
            System.out.println("Contact added successfully!");
        }
    }

    boolean isValidEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9.-]+$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email.trim());
        return !m.matches();
    }

    // Method to search for a particular contact in the address book
    Contact searchContact(String key) {
        for (Contact contact : contactList) {
            if (contact.getName().equalsIgnoreCase(key)) {
                return contact;
            }
        }
        return null;
    }

    // Method to display all the contacts present in the address book
    void listAllContacts() {
        for (Contact contact : contactList) {
            System.out.println(contact.toString());
        }
    }

    // Method to delete a specific contact from the address book
    void deleteContact(String name) {
        Contact c = searchContact(name);
        if (c != null) {
            contactList.remove(c);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("No such contact found!");
        }
    }

    // Method to update an existing contact in the address book
    void updateContact(String oldName, String newName) {
        Contact c = searchContact(oldName);
        if (c == null) {
            System.out.println("No such contact found!");
        } else {
            c.name = newName; // Update the name of the existing Contact object
            System.out.println("Contact updated successfully!");
        }
    }

    public void writeContactsToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(contactList);
            System.out.println("Contact data written to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void readContactsFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            contactList = (ArrayList<Contact>) ois.readObject();
            System.out.println("Contact data read from file: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AddressBook adrbk = new AddressBook();
        Scanner scanner = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\nAddress Book:");
            System.out.println("1. Add Contact");
            System.out.println("2. Delete Contact");
            System.out.println("3. Search for a contact");
            System.out.println("4. Display All Contacts");
            System.out.println("5. Save Contacts to File");
            System.out.println("6. Load Contacts from File");
            System.out.println("7. Update Contact");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // consume the invalid input
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1:
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter phone number:");
                    String phoneNumber = scanner.nextLine();
                    System.out.println("Enter email:");
                    String emailAddr = scanner.nextLine();
                    adrbk.addContact(name, phoneNumber, emailAddr);
                    break;

                case 2:
                    System.out.println("Enter the name of the contact to be deleted:");
                    String deletePerson = scanner.nextLine();
                    adrbk.deleteContact(deletePerson);
                    break;

                case 3:
                    System.out.println("Enter the contact to be searched:");
                    String searchPerson = scanner.nextLine();
                    Contact foundPerson = adrbk.searchContact(searchPerson);
                    if (foundPerson != null) {
                        System.out.println("Contact Found: " + foundPerson.toString());
                    } else {
                        System.out.println("No such contact found.");
                    }
                    break;

                case 4:
                    adrbk.listAllContacts();
                    break;

                case 5:
                    System.out.print("Enter filename to save contacts: ");
                    String saveFilename = scanner.nextLine();
                    adrbk.writeContactsToFile(saveFilename);
                    break;

                case 6:
                    System.out.print("Enter filename to load contacts from: ");
                    String loadFilename = scanner.nextLine();
                    adrbk.readContactsFromFile(loadFilename);
                    break;

                case 7:
                    System.out.println("Enter the contact's original name:");
                    String originalName = scanner.nextLine();
                    System.out.println("Enter the updated name:");
                    String updatedName = scanner.nextLine();
                    adrbk.updateContact(originalName, updatedName);
                    break;

                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 0);
    }
}
