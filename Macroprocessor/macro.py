f = open ("file.txt", "r")
line = f.readline()
i = 0
j = 0
index = 1
MDT_index = 20
MNT = [[0 for x in range (3)]for y in range (5)]
MDT = [[0 for x in range(2)]for y in range (15)]

while ".code" not in line:
    if "macro" in line:
        MNT[i][0] = index
        index = index + 1
        MNT[i][1] = line[6:]
        MDT_index = MDT_index + 1
        MDT[j][0] = MDT_index
        MNT[i][2] = MDT[j][0]
        line = f.readline()
        
        while line[:4] != "mend":
            MDT[j][1] = line
            j = j + 1
            MDT_index = MDT_index + 1
            MDT[j][0] = MDT_index
            line = f.readline()
            
        MDT[j][1] = line
        j = j+1
        line = f.readline()
        i = i+1
print "mdt"
print MDT
print "mnt"
print MNT
t = 0
p = 0
if ".code" in line:
    line = f.readline()
    while "end" not in line:
        if line == MNT[t][1]:
            index = MNT[t][2]
            t = 0    
            while MDT[p][0] != index:
                if MDT[p][0] != index:
                    p = p+1
                
            while MDT[p][1] != "mend\n":
                print MDT[p][1],
                p = p+1 
            p = 0
            line = f.readline()        
            
        elif MNT[t][1] == 0:
            print line,
            t = 0
            line = f.readline()
        
        else:
            t = t+1
    print "end"
