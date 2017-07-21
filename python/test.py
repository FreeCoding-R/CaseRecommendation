import sys


file_path = sys.argv[1]
names = [ os.path.join(file_path,f) for f in os.listdir(file_path)]
print(names)