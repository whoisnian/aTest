//go:generate gomobile bind -ldflags "-s -w" -target=android/arm64 -o ./aar/hello.aar -target=android ./hello
package bind

import (
	_ "golang.org/x/mobile/bind"
)
