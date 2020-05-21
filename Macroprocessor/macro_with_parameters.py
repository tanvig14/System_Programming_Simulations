f = open ("input2.txt", "r")
line = f.readline()
i = 0
j = 0
k = 0
p = 0
t = 0
p = 0
q = 0
y = 0
pp = 1
index = 1
position = 0
MDT_index = 3
MNT = [[0 for x in range (3)]for y in range (10)]
MDT = [[0 for x in range(2)]for y in range (25)]
ALA = [[0 for x in range(4)]for y in range (12)]

while ".code" not in line:
    if "macro" in line:
        myline = line
        while "&" in myline:                               
            position = myline.index('&');
            ALA[p][1] = myline[position] + myline[position+1];
            ALA[p][2] = "#" + str(pp)
            pp = pp+1
            p = p+1
            position = position + 2
            myline = myline[position:]
        
        MNT[i][0] = index
        index = index + 1
        MNT[i][1] = line[6:9]
        
        while k<p:
            ALA[k][0] = MNT[i][1]
            k = k+1
          
        MDT_index = MDT_index + 1
        MDT[j][0] = MDT_index
        MNT[i][2] = MDT[j][0]
        line = f.readline().strip("\n")
        
        while line[:4] != "mend":
            line1 = line.split(" ")
            cardp = line1[1]
            t = 0
            while 1:
                if ALA[t][1] == cardp:
                    MDT[j][1] = line1[0] + " " + ALA[t][2]
                    #print MDT
                    #print "\n"
                    #print ALA
                    #print "\n"
                    #break
                
                elif ALA[t][1] == 0:
                    MDT[j][1] = line1[0] + " " + line1[1]
                    break
                t = t+1
                
            j = j + 1
            MDT_index = MDT_index + 1
            MDT[j][0] = MDT_index
            line = f.readline().strip("\n")
            
        MDT[j][1] = line
        line = f.readline()
        pp = 1
        i = i+1
        j = j+1
      
t = 0
p = 0
q = 0
y = 0
if ".code" in line:
    line = f.readline()
    while "end" not in line:
        line1 = line.split(" ")
        params = line1[1].split(",")
     
        if line1[0] == MNT[t][1]:
            index = MNT[t][2]
            t = 0    
            while MDT[p][0] != index:
                p = p+1
                if MDT[p][0] == index:
                    break
                
            while MDT[p][1] != "mend":
                ltemp = MDT[p][1]
                l1 = ltemp.split(" ")
                
                while 1:           
                    if ALA[q][0] == line1[0] and ALA[q][2] == l1[1]:
                        ap = params[y]
                        y = y+1
                        ALA[q][3] = ap
                        print l1[0] + " " + ap
                        break
                    
                    elif ALA[q][2] == 0:
                        print ltemp
                        break
                           
                    q = q+1
                
                q = 0
                p = p+1 
            p = 0
            y = 0
            line = f.readline()        
            
        elif MNT[t][1] == 0:
            print line,
            t = 0
            line = f.readline()
        
        else:
            t = t+1
    
    print "end"    
#print "\nmdt"
#print MDT
#print "\nmnt"
##print MNT
#print "\nala"
#print ALA
