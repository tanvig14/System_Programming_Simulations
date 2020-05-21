fp = open (“main.cpp”, "r")
line = fp.readline()
i = 0
k = 0
GLOBALS = [["" for x in range (3)] for y in range (5)]
EXTERNALS = [["" for x in range (4)] for y in range (5)]

while 1:
    if "#include " in line:
        ex_name = line[10:]
        ex_name = ex_name[:-2]
        fp_ex = open(ex_name, "r")
        break
    line = fp.readline()
    
#print line
line = fp.readline().strip("\n").strip(";")    
while "int main()" not in line:
    l = line.split(" ")
    #print l
    if l[0] == "int":
        GLOBALS[k][2] = "2"
    elif l[0] == "float":
        GLOBALS[k][2] = "4"
    elif l[0] == "char":
        GLOBALS[k][2] = "1"
    GLOBALS[k][0] = l[0]
    GLOBALS[k][1] = l[1]
    k = k + 1
    line = fp.readline().strip("\n").strip(";")
    
print "\nGLOBAL VARIABLE TABLE:\nDatatype  Name  Size"
j = 0
for x in GLOBALS:
    if GLOBALS[j][0] == '':
        continue
    else:
        print x
    j = j + 1
    
ex_line = fp_ex.readline().strip("\n").strip(";")

while "}" not in ex_line:
    if "extern" in ex_line:
        line1 = ex_line.split(" ")
        #print line1
        if line1[1] == "int":
            EXTERNALS[i][3] = "2"
        elif line1[1] == "float":
            EXTERNALS[i][3] = "4"
        elif line1[1] == "char":
            EXTERNALS[i][3] = "1"        
        EXTERNALS[i][0] = line1[1]
        EXTERNALS[i][1] = line1[2]
        EXTERNALS[i][2] = line1[4]
        i = i + 1
    ex_line = fp_ex.readline().strip("\n").strip(";")

print "\nEXTERNAL VARIABLE TABLE:\nDatatype  Name  Value  Size"
j = 0
for x in EXTERNALS:
    if EXTERNALS[j][0] == '':
        continue
    else:
        print x
    j = j + 1
print "\n"
