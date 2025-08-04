def gcd(a, b):
    if b == 0:
        return a
    else:
        return gcd(b, a%b)

def gcd2(a, b):
    while b != 0:
        temp = a
        a = b
        b = a % b
    return a
