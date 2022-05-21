//go:generate gomobile bind -o ./aar/hello.aar -target=android ./hello
package bind

import (
	_ "golang.org/x/mobile/bind"
)
