package main

import (
	"fmt"
	"strings"
	"os"
	"bufio";
)

func jai_ram_ji_ki(s string) string {
	chars := []rune(s)
	for i, j := 0, len(chars)-1; i < j; i, j = i+1, j-1 {
		chars[i], chars[j] = chars[j], chars[i]
	}
	return string(chars)
}

func EncryptDecrypt(input, key string) (output string) {
        for i := 0; i < len(input); i++ {
                output += string(input[i] ^ key[i % len(key)])
        }

        return output
}

func mandir_wahi_banega(s string) string {
	words := strings.Fields(s)
	for i, j := 0, len(words)-1; i < j; i, j = i+1, j-1 {
		words[i], words[j] = jai_ram_ji_ki(words[j]), jai_ram_ji_ki(words[i])
	}
	return strings.Join(words, "_")
}

func main() {
	fmt.Print("Enter Password: ");
        user_input,_,err := bufio.NewReader(os.Stdin).ReadLine();
        if err != nil {
            fmt.Println("Something is wrong with your computer, ",err);
	}
	ency := string([]byte{33,33,116,65,51,114,71,95,115,49,95,103,110,49,77,77,97,82,103,48,114,80,95,48,103})
	if jai_ram_ji_ki(mandir_wahi_banega(string(user_input))) == ency {
        fmt.Println("You Cracked it, A Hero is born");
    	} else {
        fmt.Println("Don't Worry, Relax, Chill and Try harder");
	}


	
}
