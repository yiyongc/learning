package serializer

import (
	"google.golang.org/protobuf/encoding/protojson"
	"google.golang.org/protobuf/proto"
)

// ProtobufToJSON converts protocol buffer message to JSON string
func ProtobufToJSON(message proto.Message) (string, error) {
	marshalOpts := protojson.MarshalOptions{
		Indent:          "  ",
		UseProtoNames:   false,
		UseEnumNumbers:  false,
		EmitUnpopulated: true,
	}
	json, err := marshalOpts.Marshal(message)
	if err != nil {
		return "", err
	}
	return string(json), nil
}
