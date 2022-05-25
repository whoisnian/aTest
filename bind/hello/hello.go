package hello

import (
	"io"
	"net/http"
	"strconv"
)

func Greetings(name string) string {
	return "Hello, " + name + "!"
}

func GetCnt() int64 {
	resp, err := http.Get("https://counter.whoisnian.workers.dev/cnt")
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()
	return readAsInt(resp.Body)
}

func IncCnt() int64 {
	resp, err := http.Get("https://counter.whoisnian.workers.dev/inc")
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
