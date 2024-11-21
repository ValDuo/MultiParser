k = 10000
percentage = 0.78
tries = 0
while int(k) > 50:
    tries+= k//50
    new_k = k * percentage
    not_recieved = k - new_k
    k = not_recieved


print(tries)