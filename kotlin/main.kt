// Calculating the rsa alglorithm
class Rsa(primep: Int, primeq: Int) {
    var p: Int
    var q: Int
    val pq: Int get() = this.p * this.q
    var n: Int
    init {
        p = primep
        q = primeq
        n = (p - 1) * (q - 1)
    }

    // input should be where there user wants range of prime number to be selected from
    fun publickey(x: Int): Pair<Int, Int> {
        if (x < 2) {
            throw Exception("invalid number calculate_e $x")
        } else {
            // we are going to check if a no. is prime according to range given
            // we append the prime values to an array
            // check the gcd of the prime values
            // we also append the prime values with a gcd of one to an array
            // pick the lowest value with gcd of one from the array to reduce working with large values
            // we've gotten e
            for (i in 2..x) {
                var primeno_array = checkPrime(i)
                for (no in primeno_array) {
                    if (no >= 2) {
                        var (isgcd, value) = gcd(no, n)
                        if (isgcd) {
                            return Pair(value, pq)
                        }
                    }
                }
            }
        }
        return Pair(0, 0)
    }

    // used to calculate private key
    fun privatekey(e: Int): Pair<Int, Int> {
        // formula for calculating d = ((n*i) +1)/e
        // i is unknown and will help us get d
        // d should have a modulus of 0
        // d is out private key
        var number = 1
        var d = 0
        while (number < 100) {
            if ((((n * number) + 1) % e) == 0) {
                return Pair(((n * number) + 1) / e, pq)
            }
            number++
        }
        return Pair(d, 0)
    }
}

// getting the gcd of two values and the gcd should be one
// for a number to have a gcd of one the division of the two number
// should not give you a modulus 0.
fun gcd(smallvalue: Int, largevalue: Int): Pair<Boolean, Int> {
    if (largevalue % smallvalue != 0) {
        return Pair(true, smallvalue)
    }

    return Pair(false, smallvalue)
}

// NOTE:Decpracate this not in use
// getting the lowest value of the IntArray
fun lowestvalue(args: IntArray): Int {
    val mutableArray = args.toMutableList()
    return mutableArray.min()
}

// checking which values are primeno and appending them to an array
fun checkPrime(num: Int): IntArray {
    var flag = false
    var primenumbers: IntArray = intArrayOf(0)
    for (i in 2..num / 2) {
        // condition for nonprime number
        if (num % i == 0) {
            flag = true
        }
    }
    if (!flag) {
        var x = addElement(primenumbers, num)
        return x
    }
    return primenumbers
}

// add values to an IntArray
fun addElement(arr: IntArray, element: Int): IntArray {
    val mutableArray = arr.toMutableList()
    mutableArray.removeIf { x -> x in 0..1 }
    mutableArray.add(element)
    return mutableArray.toIntArray()
}

fun main() {
    System.loadLibrary("modulus")
    var rsa = Rsa(137, 181)
    var (public_key, public_value) = rsa.publickey(20)
    // println(rsa.publickey(29))
    var (private_key, private_value) = rsa.privatekey(public_key)
// number 5 will represent our message letter B
    var encryptmessage = encrypt(5, public_key, public_value)
    println("encrypted message is $encryptmessage")
    var decryptmessage = encrypt(encryptmessage, private_key, private_value)
    println("decrypted message is $decryptmessage")
    // println(rsa.privatekey(public_key))
}
