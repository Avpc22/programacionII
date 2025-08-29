_analisis_

## 🛍️ Tienda de Ropa - POO

nuestra idea de negocio es una **tienda de ropa virtual** desarrollada en **Java (POO)**.  
Permite al usuario explorar productos, agregarlos a un carrito, calcular el total y realizar el pago., buscando un proyecto en el que podamos incluir todos los conocimiemntos adquiridos en el curso de programación I, más la introducción de POO. a continuación presentaremos las etapas del analisis, y especificaremos los objetivos y vias para llegar a estos:

## ✨ Características

**Objetivo del sistema:**

-Simular una tienda de ropa en la que los clientes puedan:

-Navegar por el catálogo de productos.

-Agregar ropa al carrito.

-Ver cantidades y total de la compra.

-Pasar al proceso de pago.

**posibles objetos a usar en el código:**

-Producto: Representa cada prenda de ropa.

Carrito : Contiene los productos seleccionados y maneja las cantidades.

Cliente : Usuario que compra en la tienda.

Compra / Pedido : Representa la transacción de compra.

Pago : Maneja el proceso de pago.

**Atributos y Métodos de las clases**

## 🧥 Clase Producto

Atributos:

idProducto, data type (int)

nombre, data type (String)

precio, data type (double)

talla, data type (String)

cantidadDisponible, data type (int)

Métodos:

mostrarInfo() para mostrar detalles de la prenda.

actualizarStock(int cantidad) para restar de la cantidad inicial (stock) de productos, sumar los nuevos ingresos de mercancia y mantener al dia el inventario.

## 🛒 Clase Carrito

Atributos:

listaProductos 

Métodos:

agregarProducto(Producto 1, int cantidad)

eliminarProducto(Producto 1)

mostrarCarrito()

calcularTotal()

## 👤 Clase Cliente

Atributos:

nombre (String)

email (String)

Métodos:

agregarAlCarrito(Producto 1, int cantidad)

realizarCompra(Carrito 1)

## 🧾 Clase Compra

Atributos:

idCompra (int)

cliente (Cliente)

carrito (Carrito)

total (double)

Métodos:

generarFactura()

confirmarCompra()

## 💳 Clase Pago

Atributos:

monto (double)

metodoPago (String) → (Tarjeta, Efectivo, Transferencia)

Métodos:

procesarPago()

validarPago()