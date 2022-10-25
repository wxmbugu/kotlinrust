/** This file is used as a namespace for all the exported Rust functions. */
@file:JvmName("RustLib")

/** Checks if the public key pair and private key pair return the same messsage */
external fun encrypt(message: Int, power: Int, modno: Int): Int
