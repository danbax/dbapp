package enums;

public enum Actions {
	/*login*/
	ValidLoginDataCheck, // checks if user exist in the database
	// response from server:
		UsernameExist,
		UsernameDoesNotExist,
		
	Logout,

	// update catalog
	GetProducts, // return arrayList<Product> of all Products
	
	/*products*/
	UpdateProduct,
	DeleteProduct,
	// response from server:
		DeletedProduct, // product added to database
		DeletedProductError, // error while adding product to database
	AddProduct,
	// response from server:
		ProductAdded, // product added to database
		ProductAddedError, // error while adding product to database
		
	/*users manager	 */
	GetUsers,
	updateUser,
	DeleteUser;
}
