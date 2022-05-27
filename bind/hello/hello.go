package hello

import (
	"bytes"
	"io"
	"net/http"
	"strconv"
)

func Greetings(name string) string {
	return "Hello, " + name + "!"
}

func GetCnt() int64 {
	resp, err := http.Get("https://counter.whoisnian.com/cnt")
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()
	return readAsInt(resp.Body)
}

func IncCnt() int64 {
	resp, err := http.Get("https://counter.whoisnian.com/inc")
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()
	return readAsInt(resp.Body)
}

func readAsInt(r io.Reader) int64 {
	buf, err := io.ReadAll(r)
	if err != nil {
		panic(err)
	}
	res, err := strconv.ParseInt(string(buf), 10, 64)
	if err != nil {
		panic(err)
	}
	return res
}

func ProcessCommandApdu(in []byte) []byte {
	if bytes.Equal(in, []byte{0x00, 0xB0, 0x96, 0x00, 0x00}) {
		return []byte("processCommandApdu successfully\x90\x00")
	} else {
		return []byte{0x90, 0x00}
	}
}
