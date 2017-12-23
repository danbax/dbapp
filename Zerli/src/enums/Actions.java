package enums;

public enum Actions {
	/*login*/
	ValidLoginDataCheck, // checks if user exist in the database
	// options:
	UsernameExist,
	UsernameDoesNotExist,
	
	// update catalog
	GetProducts; // return arrayList<Product> of all Products
	
	
}
