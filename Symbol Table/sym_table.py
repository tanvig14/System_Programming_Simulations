fin = open("input.txt","r")
var = fin.readline()
address = 98
print "\nSymbol" + "\t" + "Address" + "\t" + "Size"
while var != "":
    var = fin.readline()
    if "int" in var:
        func(1)            
        
    elif "float" in var:
        func(2)
        
    elif "char" in var:
        func(3)
    
    def func(p):
        length = len(var)
        string = 0
        global address
        while string < length:    
            if var[string] == "," or var[string] == ";":
                index = string
                symbol = var[string-1]
                string = string + 1
                
                if p == 1:   #int
                    size = 2
                    address = address + size
                    print symbol + "\t" + str(address) + "\t" + str(size)   
    
                elif p == 2:   #float
                    size = 4
                    address = address + size
                    print symbol + "\t" + str(address) + "\t" + str(size)
            
                elif p == 3:   #char
                    size = 1
                    address = address + size
                    print symbol + "\t" + str(address) + "\t" + str(size) + "\n"
                
                  
            elif var[string] == "":
                string = string + 1
                
            else:
                string = string + 1
