insert into product_type(id, description, type) VALUES (1, 'Food products', 'FOOD');
insert into product_type(id, description, type) VALUES (2, 'Clothing items', 'CLOTHES');
insert into product_type(id, description, type) VALUES (3, 'Toys', 'TOYS');
insert into product(id,name,type_id,price,quantity,date_updated,product_image) values(1, 'Brownie',1,65,48,CURRENT_TIMESTAMP,'icons/brownie.png')
insert into product(id,name,type_id,price,quantity,date_updated,product_image) values(2, 'Muffin',1,100,36,CURRENT_TIMESTAMP,'icons/muffin.png');
insert into product(id,name,type_id,price,quantity,date_updated,product_image) values(3, 'Cake Pop',1,135,24,CURRENT_TIMESTAMP,'icons/cake-pop.png');
insert into product(id,name,type_id,price,quantity,date_updated,product_image) values(4, 'Apple tart',1,150,60,CURRENT_TIMESTAMP,'icons/apple-tart.png');
insert into product(id,name,type_id,price,quantity,date_updated,product_image) values(5, 'Water',1,150,30,CURRENT_TIMESTAMP,'icons/water.png');
insert into product(id,name,type_id,price,quantity,date_updated,product_image) values(6, 'Shirt',2,200,48,CURRENT_TIMESTAMP,'icons/shirt.png')
insert into product(id,name,type_id,price,quantity,date_updated,product_image) values(7, 'Pants',2,300,36,CURRENT_TIMESTAMP,'icons/pants.png');
insert into product(id,name,type_id,price,quantity,date_updated,product_image) values(8, 'Jacket',2,400,4,CURRENT_TIMESTAMP,'icons/jacket.png');
insert into product(id,name,type_id,price,quantity,date_updated,product_image) values(9, 'Toy',3,100,10,CURRENT_TIMESTAMP,'icons/toy.png');
insert into retail_seller(id,name,hired_on) values(1,'retail seller',current_date);
insert into users(id,username,password,enabled,seller_id)values(1,'user','{noop}pwd',true,1);
insert into authorities(id,username,authority)values(1,'user','ROLE_USER');

