
class Node:
    RED = True
    BLACK = False
    
    def __init__(self, key, val, color):
        self.left = None
        self.right = None
        self.key = key
        self.value = val
        self.color = color
        
        

class RedBlackBST:
    
    def __init__(self):
        self.root = None
    
    def put(self, key, val):
        self.root = self.putNode(self.root, key, val)
        self.root.color = Node.BLACK
    
    def putNode(self, node, key, val):
        
        if node == None:
            return Node(key, val, Node.RED)
        
        if node.key > key:
            node.left = self.putNode(node.left, key, val)
        elif node.key < key:
            node.right = self.putNode(node.right, key, val)
        else:
            node.value = val
        
        if self.isRed(node.right) and (not self.isRed(node.left)):
            node = self.rotateLeft(node)
        if self.isRed(node.left) and self.isRed(node.left.left):
            node = self.rotateRight(node)        
        if self.isRed(node.right) and self.isRed(node.left):
            self.flipColor(node)
            
        return node    
        
    
    def isRed(self, node):
        if node == None:
            return False
        
        return node.color == Node.RED
    
    def rotateLeft(self, h):
        x = h.right
        h.right = x.left
        x.left = h
        x.color = x.left.color
        x.left.color = Node.RED
        return x
    
    def rotateRight(self, h):
        x = h.left
        h.left = x.right
        x.right = h
        x.color = x.right.color
        x.right.color = Node.RED
        return x
    
    def flipColor(self, h):
        h.color = not h.color;
        h.left.color = not h.left.color;
        h.right.color = not h.right.color;
    
    def leveltravse(self):
        out = []
        out.append(self.root)
        keys = []
        #keys.append(str(self.isRed(self.root)))
        while len(out) != 0:
            node = out.pop(0)
            
            if node == None:
                continue
            keys.append(node.key)
            #keys.append(str(self.isRed(node)))
            out.append(node.left)
            out.append(node.right)
            
        print (" ".join(keys))
    
if __name__ == "__main__":
    
    tree1 = RedBlackBST()
    for i, k in enumerate("69 56 84 29 66 79 93 14 41 90 64 54 89".split()):
        tree1.put(k.strip(), i)
        
    tree1.leveltravse()
    