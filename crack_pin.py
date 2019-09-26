import hashlib
import binascii

checkPin = [55,14,75,196,13,126,208,184,168,99,174,146,214,121,217,162,94,139,101,121,116,173,124,174,36,159,191,247,187,32,6,75]

# too lazy, convery bytearray to hex string
checkPin = binascii.hexlify(bytearray(checkPin)).decode('ascii')


# The pin is a 6 digit UTF-8 string
def check_pin(pin):
    hash = hashlib.sha256(bytes(pin, 'utf-8')).hexdigest()

    if hash == checkPin:
        print("WINNER " +  pin)
        return True

    return False
    

for i in range(100000, 999999 + 1):
    if check_pin(str(i)):
        break
