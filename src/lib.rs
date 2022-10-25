// This is the interface to the JVM that we'll call the majority of our
// methods on.
use jni::JNIEnv;
// These objects are what you should use as arguments to your native
// function. They carry extra lifetime information to prevent them escaping
// this context and getting used after being GC'd.
use jni::objects::JClass;

// This is just a pointer. We'll be returning it from our function. We
// can't return one of the objects with lifetime information because the
// lifetime checker won't let us.
use jni::sys::jint;
extern crate num_bigint;
use num::ToPrimitive;
use num_bigint::BigInt;
// This keeps Rust from "mangling" the name and making it unique for this
// crate.
#[no_mangle]
pub extern "system" fn Java_RustLib_encrypt(
    _env: JNIEnv,
    // This is the class that owns our static method. It's not going to be used,
    // but still must be present to match the expected signature of a static
    // native method.
    _class: JClass,
    message: jint,
    power: jint,
    modno: jint,
) -> jint {
    let message = message as i32;
    let power = power as u32;
    let modno = modno as i32;
    rsa_alg(message, power, modno) as jint
}

// rsa_alg for decryption and encryption message during step 7 & 8
fn rsa_alg(message: i32, power: u32, modno: i32) -> i32 {
    let encrypt = BigInt::from(message);
    let power = encrypt.pow(power);
    let ans = power % modno;
    ans.to_i32().unwrap()
}
