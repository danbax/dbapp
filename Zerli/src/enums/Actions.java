package enums;

public enum Actions {
	/*login*/
	ValidLoginDataCheck, // checks if user exist in the database
	// response from server:
	UsernameExist,
	UsernameDoesNotExist,
	
	// update catalog
	GetProducts, // return arrayList<Product> of all Products
	AddProduct,
	// response from server:
	ProductAdded, // product added to database
	ProductAddedError; // error while adding product to database
	
}
