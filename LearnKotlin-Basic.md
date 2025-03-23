# Demo
Demo simple java Springboot application

# Learning Kotlin for Java developer

## Basic types and functions

Compare to Java, all Kotlin variable are object with specific type; declared with pascal syntax **Name**: **Type**
Using **val** (immutable) or **var** (non-immutable) to declare variables.  Kotlin use type inference to automatically detect variable type.
Most of the type you do not have to declare variable type.  

Another important feature in Kotlin is **Null safety** feature; this is a designed to significantly reduce the risk of null references with kotlin variable.
Kotlin allow variable to be null by using **?** behind the type. 

Ref: [Kotlin Null Safety](https://kotlinlang.org/docs/null-safety.html#nullable-types-and-non-nullable-types)

Example:

    val x : Int = 9         // immutable variable 
    val x = 9               // Let the compiler infer the type
                            // Similar to java: Var x = 9
    var y = 11              // mutable variable
    var z : Int? = null     // nullable mutable variable
    // Defining Anonymous function and assigned to variable.
    val f = fun ( x: Int = 9 ) {
        return 9.0
    }

Since

Additional type that differ from Java:

    Any : Root of all Kotlin class hierarchy.  This similar to Object class
    val myStringArray: Array<String> 

    Nothing : Represent a value that never exist.  ( no instance of this class )

    Unit : Similar to Java void

Example:

    fun printCheckIntStringType ( input : Any ) : Unit = when  {
        input is String -> println("This is a string type input")
        input is Int	-> println("This is a Int type input")
        input < 2.0 -> println("This is a Double less than 2.0")
        else -> println(" Type: " + input.javaClass)
    }

### Function

Declaring function as follows:

    fun functionName (params =<defaultValue>,...) : returnType { 
        :
        // Function body
        return returnType
    }

Kotlin function are first class; means it can be assigned to variables, part of data structure, or can be used as parameters, nbe declaring as a **function type**

Example:

    fun geoLocationFromAddress(address: USAddress,
        onComplete : ((geoData: GeoData, location : GeoAddressLocation) -> Unit)? ) : GeoAddressLocation? {
    :
    val geoLocation = geoLocationFromAddress(USAddress("1234 Any St", "Anytown", "NY", "12345")) {
        Logger.info("Address: ${address.formated()} is located in longitute: ${it.lng}, latitute: ${it.lat} ")
    }

You can review functions from kotlin site [Kotlin Function](https://play.kotlinlang.org/byExample/01_introduction/02_Functions) 

Lambda:

    (params =<defaultValue>,...) -> {
        :
        // Function body
    }

## Null safety
Example

    fun process(x: Int?): Int { 
        println("Value: " + x!!) // not-null assertion operator !!
        x.let{ y = x + 10 }    // If x is not null add 10 to x
        return y
    }

    fun processNonNull(x: Int): Int { 
        println("Value: ${x}")
        return x + 10    // If x is not null add 10 to x
    }

    fun printNonNullValue( param1: Int ): Unit{
        if ( param1 != null){
            val y = param1 + 10
            prinln("Y = $y")
        }
        // Smart cast changed param1 to non-nullable variable
        prinln("Y ="+println(processNonNull(param1)))
        
    }

    fun main ( args: Array<String> ){
        
        val x = process ( y )
        
        // converting nullable to non-nullable
        if ( x != null ) printNonNullValue(x)

        // converting nullable to non-nullable
        printNonNullValue(x?)
        
    }







Example:

    fun getYValue(){
      return 8
    }

    fun main(){
      val x = 9
      val y = getYValue()
      val f = 
    }

* All variable type are object, there is no literal type

* Variable type in general are deduce by the system from the assingment operator.  

Example:

    val x = getValueForX()

  where function getValueForX return type will set the type of variable x

    fun getValueForX () : Long -> x:Long = getValueForX()
    fun getValueForX () : ClassY -> x:ClassY = getValueForX()
In Java 17 and above similar to utilizing **var** keyword.


### Immutable
Immutable variable denoted by keyword **val**.  As functional programming language, this is one of the key feature of Kotlin language that
allow to create a clean function that protect side effect to variable within a function.
Kotlin provides few function to help building immutable variables.  

For example

    fun getRandomValue(): Int {
        return Random(8886).nextInt()
    }

    fun getXValue(y){
        var x = 5
        val y = getRandomValue()
        if x < y {
          x = y
        }
        return x
    }
The mutable x can be converted to 

    fun getXValue(y){
        val y = getRandomValue()

        val x = if (x < y) x = y else 5
or

        val x = when {
            y == 8 -> { 7 }
            y > 8 -> { 18 }
            else -> { 28 }
        }
or for more complex processing, utilizing anonymous function 

		val x : Int = {
            val y = getRandomValue()
			val yy = if( y>100 ) y/100 else y  
			// More processing
			yy
		}()


    return x
    }

or other simpler syntax yet readable enough

    fun getXValue(y: Int = getYValue()) = if(5 < y) y else 5
or

    fun getXValue(y) = when {
            y == 8 -> { 7 }
            y > 8 -> { 18 }
            else -> { 28 }
        }
  
  
  